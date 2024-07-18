package com.github.ethpalser.data;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class DataReader {

    public DataReader() {
        // empty
    }

    public Object read(Class<?> type, String filePath) {
        Gson gson = new GsonBuilder().serializeNulls().create();
        try (FileReader reader = new FileReader(filePath)) {
            JsonReader jsonReader = new JsonReader(new BufferedReader(reader));
            return gson.fromJson(jsonReader, type);
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }
        return null;
    }

    public SaveData readSaveFile(String fileName) {
        return (SaveData) this.read(SaveData.class,SaveData.FILE_DIR + fileName);
    }

    public PieceData readPiecesFile(String fileName) {
        return (PieceData) this.read(PieceData.class, PieceData.FILE_DIR + fileName);
    }
}
