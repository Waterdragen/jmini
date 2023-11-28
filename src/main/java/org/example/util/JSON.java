package org.example.util;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;

public class JSON {
    public static <T> Object loadJSON(String path, Class<T> cls) throws IOException {
        var objectMapper = new ObjectMapper();
        var file = new File(path);
        return objectMapper.readValue(file, cls);
    }
}
