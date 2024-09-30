package com.ethpalser.data;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;

public class Saves {

    public static String PIECE_FILE_DIR = "./data/pieces/";
    public static String PIECE_FILE_NAME = "pieces";
    public static String GAME_FILE_DIR = "./data/saves/";
    public static String GAME_FILE_NAME = "save";

    public static void writeAsJson(Object object, Type type, String filePath) {
        Gson gson = new GsonBuilder().serializeNulls().create();
        try (FileWriter writer = new FileWriter(filePath)) {
            JsonWriter jsonWriter = new JsonWriter(writer);
            gson.toJson(object, type, jsonWriter);
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }
    }

    public static void writeAsString(String text, String filePath, String fileName) {
        try {
            Files.createDirectories(Path.of(filePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
        String fullPath = filePath + fileName;
        File file = new File(fullPath);
        if (text != null) {
            try (FileWriter writer = new FileWriter(fullPath)) {
                writer.write(text);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static Object readAsJson(Class<?> type, String filePath) {
        Gson gson = new GsonBuilder().serializeNulls().create();
        try (FileReader reader = new FileReader(filePath)) {
            JsonReader jsonReader = new JsonReader(new BufferedReader(reader));
            return gson.fromJson(jsonReader, type);
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }
        return null;
    }

    public static String readAsString(String filePath, String fileName) {
        String fullPath = filePath + fileName;
        File file = new File(fullPath);
        if (file.exists()) {
            try (Scanner scanner = new Scanner(file)) {
                StringBuilder sb = new StringBuilder();
                while (scanner.hasNext()) {
                    sb.append(scanner.next());
                }
                return sb.toString();
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            }
        }
        return null;
    }

    public static String sessionToJson(Session session) {
        if (session == null) {
            return null;
        }
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(session);
    }

    public static Session sessionFromJson(String json) {
        if (json == null) {
            return null;
        }
        Gson gson = new Gson();
        return gson.fromJson(json, Session.class);
    }
}
