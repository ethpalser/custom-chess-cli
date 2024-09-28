package com.ethpalser.data;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;

public class DataWriter {

    public DataWriter() {
        // empty
    }

    public void write(Object object, Type type, String filePath) {
        Gson gson = new GsonBuilder().serializeNulls().create();
        try (FileWriter writer = new FileWriter(filePath)) {
            JsonWriter jsonWriter = new JsonWriter(writer);
            gson.toJson(object, type, jsonWriter);
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }
    }

    public void write(String text, String filePath, String fileName) {
        try {
            Files.createDirectories(Path.of(filePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
        String fullPath = filePath + "/" + fileName;
        File file = new File(fullPath);
        if (text != null) {
            try (FileWriter writer = new FileWriter(fullPath)) {
                writer.write(text);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
