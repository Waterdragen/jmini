package org.example.util;

import java.util.HashSet;

public final class Consts {
    // punctuation chars
    public static final String PUNCT = "()\";:,.?!";

    // Set of allowed characters
    public static final HashSet<String> NAME_SET = getNameSet();

    // letter to indicate an empty space
    public static final String FREE_CHAR = "~";

    // row map for standard
    public static final String[] FMAP_STANDARD = {"LP", "LR", "LM", "LI", "LI", "RI", "RI", "RM", "RR", "RP"};

    // row map for angle
    public static final String[] FMAP_ANGLE = {"LR", "LM", "LI", "LI", "LI", "RI", "RI", "RM", "RR", "RP"};

    private static HashSet<String> getNameSet() {
        String s = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789 _-'():~";
        HashSet<String> nameSet = new HashSet<>();
        for (int i = 0; i < s.length(); i++) {
            nameSet.add(s.substring(i, i + 1));
        }
        return nameSet;
    }
}
