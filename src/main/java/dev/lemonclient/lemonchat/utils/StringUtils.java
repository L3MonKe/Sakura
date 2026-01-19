package dev.lemonclient.lemonchat.utils;

public class StringUtils {
    public static String getReplaced(String str, Object... args) {
        for (Object arg : args) {
            str = str.replaceFirst("\\{}", arg.toString());
        }
        return str;
    }
}
