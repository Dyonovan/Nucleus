package com.pauljoda.nucleus.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.pauljoda.nucleus.Nucleus;

import java.io.*;

public class JsonUtils {
    /**
     * Writes an object to a JSON file.
     *
     * @param toWrite the object to write to JSON
     * @param path    the path to the JSON file
     * @return true if the object is successfully written to the JSON file, false otherwise
     */
    public static boolean writeToJson(Object toWrite, String path) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(toWrite);
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(path));
            writer.write(json);
            writer.close();
            return true;
        } catch (IOException e) {
            Nucleus.LOGGER.warn("Failed to write to: " + path);
            return false;
        }
    }

    /**
     * Reads data from a JSON file and converts it to the specified type using Gson.
     *
     * @param <C>  The type of object to convert the JSON data to.
     * @param type The TypeToken representing the type of object to convert the JSON data to.
     * @param path The path to the JSON file.
     * @return The converted object of the specified type, or null if the file was not found.
     */
    public static <C> C readFromJson(TypeToken type, String path) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(path));
            Gson gson = new Gson();
            return gson.fromJson(reader, type.getType());
        } catch (FileNotFoundException e) {
            Nucleus.LOGGER.warn("Could not find file: " + path);
            return null;
        }
    }
}