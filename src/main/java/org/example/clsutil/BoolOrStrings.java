package org.example.clsutil;

import java.util.Arrays;

public class BoolOrStrings {
    private final Boolean b;
    private final String[] s;
    private BoolOrStrings(Boolean b, String[] s) {
        this.b = b;
        this.s = s;
    }
    public static BoolOrStrings fromBool(boolean b) {
        return new BoolOrStrings(b, null);
    }
    public static BoolOrStrings fromStrings(String[] s) {
        assert s != null;
        return new BoolOrStrings(null, s);
    }
    public boolean isBool() {
        return this.b != null;
    }
    public boolean isStrings() {
        return this.s != null;
    }
    public boolean getBool() {
        assert this.b != null;
        return this.b;
    }
    public String[] getStrings() {
        assert this.s != null;
        return this.s;
    }
    @Override
    public String toString() {
        if (isBool())
            return b.toString();
        else
            return Arrays.toString(s);
    }
}