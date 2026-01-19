package dev.lemonclient.lemonchat.utils;

import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public enum ChatFormatting {
    BLACK("BLACK", '0', 0, 0),
    DARK_BLUE("DARK_BLUE", '1', 1, 170),
    DARK_GREEN("DARK_GREEN", '2', 2, 43520),
    DARK_AQUA("DARK_AQUA", '3', 3, 43690),
    DARK_RED("DARK_RED", '4', 4, 11141120),
    DARK_PURPLE("DARK_PURPLE", '5', 5, 11141290),
    GOLD("GOLD", '6', 6, 16755200),
    GRAY("GRAY", '7', 7, 11184810),
    DARK_GRAY("DARK_GRAY", '8', 8, 5592405),
    BLUE("BLUE", '9', 9, 5592575),
    GREEN("GREEN", 'a', 10, 5635925),
    AQUA("AQUA", 'b', 11, 5636095),
    RED("RED", 'c', 12, 16733525),
    LIGHT_PURPLE("LIGHT_PURPLE", 'd', 13, 16733695),
    YELLOW("YELLOW", 'e', 14, 16777045),
    WHITE("WHITE", 'f', 15, 16777215),
    OBFUSCATED("OBFUSCATED", 'k', true),
    BOLD("BOLD", 'l', true),
    STRIKETHROUGH("STRIKETHROUGH", 'm', true),
    UNDERLINE("UNDERLINE", 'n', true),
    ITALIC("ITALIC", 'o', true),
    RESET("RESET", 'r', -1, null);

    public static final char PREFIX_CODE = '\u00a7';
    private static final Map<String, ChatFormatting> FORMATTING_BY_NAME = Arrays.stream(values()).collect(Collectors.toMap((format) -> cleanName(format.name), (format) -> format));
    private static final Pattern STRIP_FORMATTING_PATTERN = Pattern.compile("(?i)\u00a7[0-9A-FK-OR]");
    private final String name;
    private final char code;
    private final boolean isFormat;
    private final String toString;
    private final int id;

    private final Integer color;

    private static String cleanName(String name) {
        return name.toLowerCase(Locale.ROOT).replaceAll("[^a-z]", "");
    }

    ChatFormatting(String name, char code, int id, Integer color) {
        this(name, code, false, id, color);
    }

    ChatFormatting(String name, char code, boolean isFormat) {
        this(name, code, isFormat, -1, null);
    }

    ChatFormatting(String name, char code, boolean isFormat, int id, Integer color) {
        this.name = name;
        this.code = code;
        this.isFormat = isFormat;
        this.id = id;
        this.color = color;
        this.toString = "\u00a7" + code;
    }

    public char getChar() {
        return this.code;
    }

    public int getId() {
        return this.id;
    }

    public boolean isFormat() {
        return this.isFormat;
    }

    public boolean isColor() {
        return !this.isFormat && this != RESET;
    }


    public Integer getColor() {
        return this.color;
    }

    public String getName() {
        return this.name().toLowerCase(Locale.ROOT);
    }

    public String toString() {
        return this.toString;
    }

    public static String stripFormatting(String regex) {
        return regex == null ? null : STRIP_FORMATTING_PATTERN.matcher(regex).replaceAll("");
    }


    public static ChatFormatting getByName(String name) {
        return name == null ? null : FORMATTING_BY_NAME.get(cleanName(name));
    }

    public static ChatFormatting getById(int id) {
        if (id < 0) {
            return RESET;
        } else {
            for (ChatFormatting chatformatting : values()) {
                if (chatformatting.getId() == id) {
                    return chatformatting;
                }
            }

            return null;
        }
    }


    public static ChatFormatting getByCode(char code) {
        char c0 = Character.toString(code).toLowerCase(Locale.ROOT).charAt(0);

        for (ChatFormatting chatformatting : values()) {
            if (chatformatting.code == c0) {
                return chatformatting;
            }
        }

        return null;
    }

    public static Collection<String> getNames(boolean isColor, boolean isFormat) {
        List<String> list = new ArrayList<>();

        for (ChatFormatting chatformatting : values()) {
            if ((!chatformatting.isColor() || isColor) && (!chatformatting.isFormat() || isFormat)) {
                list.add(chatformatting.getName());
            }
        }

        return list;
    }

    public String getSerializedName() {
        return this.getName();
    }
}
