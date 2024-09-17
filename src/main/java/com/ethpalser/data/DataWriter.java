package com.ethpalser.data;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;

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

    public void writeSaveFile(SaveData saveData, String fileName) {
        this.write(saveData, SaveData.class, SaveData.FILE_DIR + fileName);
    }

    public void writePieceFile(PieceData pieceData, String fileName) {
        this.write(pieceData, PieceData.class, PieceData.FILE_DIR + fileName);
    }

}
