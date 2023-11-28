package org.example.clsutil;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class LinksJSON extends HashMap<String, String> {
    public static LinksJSON loadLinks(String path) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        // Read JSON file
        File jsonFile = new File(path);
        return objectMapper.readValue(jsonFile, LinksJSON.class);
    }
}
