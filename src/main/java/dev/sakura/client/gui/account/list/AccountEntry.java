package dev.sakura.client.gui.account.list;

import dev.sakura.client.account.type.MinecraftAccount;
import dev.sakura.client.account.type.impl.CrackedAccount;
import dev.sakura.client.account.type.impl.MicrosoftAccount;
import dev.sakura.client.account.util.TextureDownloader;
import dev.sakura.client.manager.Managers;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.widget.AlwaysSelectedEntryListWidget;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.session.Session;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import static org.lwjgl.glfw.GLFW.GLFW_MOUSE_BUTTON_1;

public class AccountEntry extends AlwaysSelectedEntryListWidget.Entry<AccountEntry> {
    private static final TextureDownloader FACE_DOWNLOADER = new TextureDownloader();

    private final MinecraftAccount account;
    private long lastClickTime = -1;

    public AccountEntry(MinecraftAccount account) {
        this.account = account;
    }

    @Override
    public void render(DrawContext context, int index, int y, int x, int entryWidth, int entryHeight, int mouseX, int mouseY, boolean hovered, float tickDelta) {
        // 使用原生渲染头像
        if (account instanceof CrackedAccount || (account instanceof MicrosoftAccount msa && msa.getUsernameOrNull() != null)) {
            final String id = "face_" + account.username().toLowerCase();
            if (!FACE_DOWNLOADER.exists(id)) {
                if (!FACE_DOWNLOADER.isDownloading(id)) {
                    FACE_DOWNLOADER.downloadTexture(id,
                            "https://minotar.net/helm/" + account.username() + "/15", false);
                }
            } else {
                final Identifier texture = FACE_DOWNLOADER.get(id);
                if (texture != null) {
                    int avatarY = y + (entryHeight - 16) / 2;
                    context.drawTexture(RenderLayer::getGuiTextured, texture, x + 4, avatarY, 0f, 0f, 16, 16, 16, 16);
                }
            }
        }
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (button == GLFW_MOUSE_BUTTON_1) {
            final long time = System.currentTimeMillis() - lastClickTime;
            if (time > 0L && time < 500L) {
                final Session session = account.login();
                if (session != null) {
                    Managers.ACCOUNT.setSession(session);
                }
            }
            lastClickTime = System.currentTimeMillis();
            return false;
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public Text getNarration() {
        if (account instanceof MicrosoftAccount msa && msa.username() == null) {
            return null;
        }
        return Text.of(account.username());
    }

    public MinecraftAccount getAccount() {
        return account;
    }
}
