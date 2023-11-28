package org.example.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.clsutil.LayoutJSON;
import org.example.clsutil.Returns;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.FileSystemException;

public class Memory {
    public static void add(LayoutJSON ll) throws Exception {
        String name = ll.getName().toLowerCase();
        String path = getLayoutPath(name);
        File file = new File(path);
        if (file.exists()) {
            throw new Returns.Error("`" + name + "` already exists");
        }
        var objectMapper = new ObjectMapper();
        objectMapper.writeValue(file, ll);
    }

    public static void update(LayoutJSON ll) throws IOException {
        String name = ll.getName().toLowerCase();
        String path = getLayoutPath(name);
        var objectMapper = new ObjectMapper();
        objectMapper.writeValue(new File(path), ll);
    }

    public static void remove(String name, long id, boolean byAdmin) throws Exception {
        String path = getLayoutPath(name);
        File file = new File(path);
        if (!file.exists()) {
            throw new FileNotFoundException("No such file \"" + path + "\"");
        }

        var layoutJSON = LayoutJSON.loadLayout(path);
        if (layoutJSON.getUser() != id && !byAdmin) {
            throw new Returns.Error("you don't own any layout named `" + name + "`");
        }

        boolean isDeleted = file.delete();
        if (!isDeleted) {
            throw new FileSystemException("Error deleting file \"" + path + "\"");
        }
    }

    public static LayoutJSON get(String name) throws Exception {
        String path = getLayoutPath(name);
        if (fileNotFound(path)) {
            throw new FileNotFoundException("No such file \"" + path + "\"");
        }

        return LayoutJSON.loadLayout(path);
    }

    public static LayoutJSON find(String name) throws Exception {
        String path = getLayoutPath(name);
        if (fileNotFound(path)) {
            String[] pool = getJSONFilePathsInFolder("layouts");
            path = Similarity.getBestMatch(name, pool);
        }

        return LayoutJSON.loadLayout(path);
    }

    public static String[] getJSONFilePathsInFolder(String folderPath) throws FileNotFoundException {
        File folder = new File(folderPath);
        File[] files = folder.listFiles();

        if (files == null) {
            throw new FileNotFoundException("No such folder \"" + folderPath + "\"");
        }

        String[] filePaths = new String[files.length];
        String path;
        for (int i = 0; i < files.length; i++) {
            path = files[i].getPath();
            // with json extension
            if (path.matches(".*\\.json$"))
                // get the relative path of the file
                filePaths[i] = files[i].getPath();
        }

        return filePaths;
    }

    private static String getLayoutPath(String name) {
        return String.format("layouts/%s.json", name);
    }

    private static boolean fileNotFound(String path) {
        return !new File(path).exists();
    }
}
