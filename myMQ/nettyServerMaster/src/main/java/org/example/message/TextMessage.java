package org.example.message;

import com.google.gson.Gson;

public class TextMessage extends Message{

    String data;

    @Override
    public String toString() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}
