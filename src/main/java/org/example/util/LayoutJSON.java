package org.example.util;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class LayoutJSON {
    private String name;
    private long user;
    private String board;
    private HashMap<String, Fingers> keys;
    private Fingers[] free;

    public String getName() { return name; }
    public long getUser() { return user; }
    public String getBoard() { return board; }
    public HashMap<String, Fingers> getKeys() { return keys; }
    public Fingers[] getFree() { return free; }

    public static class Fingers {
        private int row;
        private int col;
        private String finger;

        public int getRow() { return row; }
        public int getCol() { return col; }
        public String getFinger() { return finger; }
    }

    public static LayoutJSON loadLayout(String path) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        // Read JSON file
        File jsonFile = new File(path);
        return objectMapper.readValue(jsonFile, LayoutJSON.class);
    }
}
