package org.example.message;

import com.google.gson.Gson;

public class TextMessage {

    String messageType;
    String routerKey;

    String expiredTime;

    String priorityRank;

    String pushOrPoll;

    String delayedTime;

    String data;

    @Override
    public String toString() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}
