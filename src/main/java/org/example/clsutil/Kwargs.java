package org.example.clsutil;

import java.util.HashMap;

public class Kwargs {
    public final String arg;
    public final HashMap<String, BoolOrStrings> kwargs;

    public Kwargs(String arg, HashMap<String, BoolOrStrings> kwargs) {
        this.arg = arg;
        this.kwargs = kwargs;
    }

    @Override
    public String toString() {
        return "arg=" + this.arg + "\nkwargs = \n" + this.kwargs.toString();
    }
}
