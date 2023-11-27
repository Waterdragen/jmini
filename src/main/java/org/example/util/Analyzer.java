package org.example.util;

import java.io.IOException;

import java.util.HashMap;

public class Analyzer {
    public static class Table extends HashMap<String, String> {}
    public static Table TABLE;

    static {
        try {
            TABLE = (Table) JSON.loadJSON("table.json", Table.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static Table getTable() {
        return TABLE;
    }

}
