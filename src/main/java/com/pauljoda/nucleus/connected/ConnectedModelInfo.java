package com.pauljoda.nucleus.connected;

import com.google.gson.JsonObject;

public class ConnectedModelInfo {
    // Declares the fields
    private String model;
    private int x;
    private int y;

    // Constructs a model variant object from a JSON object
    public ConnectedModelInfo(JsonObject jsonObject) {
        // Gets the model file name from the JSON object
        this.model = jsonObject.get("model").getAsString();

        // Gets the x parameter from the JSON object, or 0 if not present
        this.x = jsonObject.has("x") ? jsonObject.get("x").getAsInt() : 0;

        // Gets the y parameter from the JSON object, or 0 if not present
        this.y = jsonObject.has("y") ? jsonObject.get("y").getAsInt() : 0;
    }

    // Returns the model file name
    public String getModel() {
        return this.model;
    }

    // Returns the x parameter
    public int getX() {
        return this.x;
    }

    // Returns the y parameter
    public int getY() {
        return this.y;
    }
}
