package dev.lemonclient.lemonchat.utils;


public enum Rank {
    Owner("Owner"),
    Developer("Developer"),
    Beta("Beta"),
    User("User");

    public final String name;

    Rank(String name) {
        this.name = name;
    }

    public static Rank get(String s) {
        for (Rank r : values()) {
            if (r.toString().equals(s)) {
                return r;
            }
        }

        return Rank.User;
    }

    public static Rank getByName(String s) {
        for (Rank r : values()) {
            if (r.name.equalsIgnoreCase(s)) {
                return r;
            }
        }

        return null;
    }
}
