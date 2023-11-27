package org.example.util;

public class Returns {
    public boolean canReturn;
    public String msg;
    Returns(boolean canReturn, String msg) {
        this.canReturn = canReturn;
        this.msg = msg;
    }

    public static Returns error(String msg) {
        return new Returns(false, "Error: " + msg);
    }

    public static Returns success() {
        return new Returns(true, "Success!");
    }
}
