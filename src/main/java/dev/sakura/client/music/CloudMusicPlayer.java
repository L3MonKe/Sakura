package dev.sakura.client.music;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.function.LongFunction;

public class CloudMusicPlayer {
    private final Object lock = new Object();
    private final List<CloudMusicService.SongItem> queue = new ArrayList<>();
    private LongFunction<String> resolver;
    private Thread worker;
    private volatile boolean stopped = false;
    private volatile boolean paused = false;
    private volatile boolean skipRequested = false;
    private volatile int currentIndex = -1;
    private volatile CloudMusicService.SongItem currentSong;
    private volatile SourceDataLine line;
    private volatile int volume = 70;
    private volatile String lastError = "";
    private volatile long positionMs = 0L;

    public CloudMusicPlayer(LongFunction<String> resolver) {
        this.resolver = resolver;
        this.worker = new Thread(this::loop, "Sakura-CloudMusic-Player");
        this.worker.setDaemon(true);
        this.worker.start();
    }

    public void setResolver(LongFunction<String> resolver) {
        this.resolver = resolver;
    }

    public void playQueue(List<CloudMusicService.SongItem> songs, int startIndex) {
        synchronized (lock) {
            queue.clear();
            queue.addAll(songs);
            if (queue.isEmpty()) {
                currentIndex = -1;
                currentSong = null;
                return;
            }
            if (startIndex < 0) {
                startIndex = 0;
            }
            if (startIndex >= queue.size()) {
                startIndex = queue.size() - 1;
            }
            currentIndex = startIndex;
            paused = false;
            skipRequested = true;
            closeLine();
            lock.notifyAll();
        }
    }

    public void playIndex(int index) {
        synchronized (lock) {
            if (queue.isEmpty()) {
                return;
            }
            if (index < 0 || index >= queue.size()) {
                return;
            }
            currentIndex = index;
            paused = false;
            skipRequested = true;
            closeLine();
            lock.notifyAll();
        }
    }

    public void next() {
        synchronized (lock) {
            if (queue.isEmpty()) {
                return;
            }
            currentIndex = Math.min(currentIndex + 1, queue.size() - 1);
            paused = false;
            skipRequested = true;
            closeLine();
            lock.notifyAll();
        }
    }

    public void previous() {
        synchronized (lock) {
            if (queue.isEmpty()) {
                return;
            }
            currentIndex = Math.max(currentIndex - 1, 0);
            paused = false;
            skipRequested = true;
            closeLine();
            lock.notifyAll();
        }
    }

    public void togglePause() {
        synchronized (lock) {
            paused = !paused;
            lock.notifyAll();
        }
    }

    public boolean isPlaying() {
        return currentSong != null && !paused;
    }

    public boolean isPaused() {
        return paused;
    }

    public CloudMusicService.SongItem getCurrentSong() {
        return currentSong;
    }

    public long getPositionMs() {
        return positionMs;
    }

    public int getCurrentIndex() {
        return currentIndex;
    }

    public List<CloudMusicService.SongItem> getQueueSnapshot() {
        synchronized (lock) {
            return List.copyOf(queue);
        }
    }

    public void setVolume(int value) {
        int normalized = Math.max(0, Math.min(100, value));
        volume = normalized;
        SourceDataLine currentLine = line;
        if (currentLine != null) {
            applyVolume(currentLine);
        }
    }

    public int getVolume() {
        return volume;
    }

    public String getLastError() {
        return lastError;
    }

    public void stop() {
        synchronized (lock) {
            stopped = true;
            skipRequested = true;
            closeLine();
            lock.notifyAll();
        }
    }

    private void loop() {
        while (!stopped) {
            CloudMusicService.SongItem song;
            synchronized (lock) {
                while (!stopped && (queue.isEmpty() || currentIndex < 0 || currentIndex >= queue.size())) {
                    currentSong = null;
                    try {
                        lock.wait();
                    } catch (InterruptedException ignored) {
                    }
                }
                if (stopped) {
                    return;
                }
                song = queue.get(currentIndex);
                currentSong = song;
                positionMs = 0L;
                skipRequested = false;
            }
            try {
                playSong(song);
            } catch (Exception ignored) {
            }
            synchronized (lock) {
                closeLine();
                if (!skipRequested && !paused && currentIndex < queue.size() - 1) {
                    currentIndex++;
                } else if (!skipRequested && !paused && currentIndex >= queue.size() - 1) {
                    currentSong = null;
                }
                skipRequested = false;
            }
        }
    }

    private void playSong(CloudMusicService.SongItem song) throws Exception {
        String url = resolver.apply(song.id());
        if (url == null || url.isBlank()) {
            lastError = "未获取到可播放链接";
            return;
        }
        positionMs = 0L;
        AudioInputStream baseStream = openAudioStream(url);
        AudioInputStream stream = baseStream;
        AudioFormat format = stream.getFormat();
        if (format.getEncoding() != AudioFormat.Encoding.PCM_SIGNED) {
            format = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, format.getSampleRate(), 16, format.getChannels(), format.getChannels() * 2, format.getSampleRate(), false);
            stream = AudioSystem.getAudioInputStream(format, stream);
        }
        DataLine.Info info = new DataLine.Info(SourceDataLine.class, format, AudioSystem.NOT_SPECIFIED);
        SourceDataLine dataLine = (SourceDataLine) AudioSystem.getLine(info);
        dataLine.open(format);
        dataLine.start();
        line = dataLine;
        applyVolume(dataLine);

        long bytesWritten = 0L;
        int frameSize = Math.max(1, format.getFrameSize());
        double frameRate = format.getFrameRate() <= 0 ? format.getSampleRate() : format.getFrameRate();
        frameRate = frameRate <= 0 ? 44100.0 : frameRate;

        byte[] buffer = new byte[2048];
        int read;
        while ((read = stream.read(buffer, 0, buffer.length)) != -1) {
            synchronized (lock) {
                while (paused && !skipRequested && !stopped) {
                    try {
                        lock.wait();
                    } catch (InterruptedException ignored) {
                    }
                }
                if (skipRequested || stopped) {
                    break;
                }
            }
            dataLine.write(buffer, 0, read);
            bytesWritten += read;
            long frames = bytesWritten / frameSize;
            positionMs = (long) ((frames * 1000.0) / frameRate);
        }
        stream.close();
        lastError = "";
    }

    private AudioInputStream openAudioStream(String url) throws Exception {
        try {
            return AudioSystem.getAudioInputStream(AudioSystem.getAudioInputStream(new URL(url)));
        } catch (Exception first) {
            try {
                return AudioSystem.getAudioInputStream(new URL(url));
            } catch (Exception second) {
                File downloaded = downloadToTempFile(url);
                try {
                    return AudioSystem.getAudioInputStream(downloaded);
                } catch (Exception third) {
                    lastError = third.getMessage() == null ? "音频解码失败" : third.getMessage();
                    throw third;
                } finally {
                    downloaded.deleteOnExit();
                }
            }
        }
    }

    private File downloadToTempFile(String audioUrl) throws Exception {
        HttpURLConnection connection = null;
        InputStream input = null;
        FileOutputStream output = null;
        File temp = File.createTempFile("sakura-cloudmusic-" + UUID.randomUUID(), ".audio");
        try {
            connection = (HttpURLConnection) new URL(audioUrl).openConnection();
            connection.setInstanceFollowRedirects(true);
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(10000);
            connection.setReadTimeout(15000);
            connection.setRequestProperty("User-Agent", "Mozilla/5.0");
            connection.setRequestProperty("Referer", "https://music.163.com");
            connection.connect();
            input = connection.getInputStream();
            output = new FileOutputStream(temp);
            byte[] buffer = new byte[4096];
            int len;
            while ((len = input.read(buffer)) != -1) {
                output.write(buffer, 0, len);
            }
            output.flush();
            return temp;
        } finally {
            try {
                if (input != null) input.close();
            } catch (Exception ignored) {
            }
            try {
                if (output != null) output.close();
            } catch (Exception ignored) {
            }
            if (connection != null) {
                connection.disconnect();
            }
        }
    }

    private void applyVolume(SourceDataLine dataLine) {
        if (dataLine.isControlSupported(FloatControl.Type.MASTER_GAIN)) {
            FloatControl gain = (FloatControl) dataLine.getControl(FloatControl.Type.MASTER_GAIN);
            float min = gain.getMinimum();
            float max = gain.getMaximum();
            float ratio = volume / 100f;
            gain.setValue(min + (max - min) * ratio);
        }
    }

    private void closeLine() {
        SourceDataLine dataLine = line;
        line = null;
        if (dataLine != null) {
            try {
                dataLine.stop();
                dataLine.flush();
                dataLine.close();
            } catch (Exception ignored) {
            }
        }
    }
}
