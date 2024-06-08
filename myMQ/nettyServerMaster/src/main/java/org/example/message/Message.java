package org.example.message;

import lombok.*;

@Data
public class Message {

    private String messageType;

    String[] targetQueue;

    String expiredTime;

    String priorityRank;

    String pushOrPoll;

    String delayedTime;

    Object data;

    public String getMessageType() {
        return messageType;
    }

    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }

    public String[] getTargetQueue() {
        return targetQueue;
    }

    public void setTargetQueue(String[] targetQueue) {
        this.targetQueue = targetQueue;
    }

    public String getExpiredTime() {
        return expiredTime;
    }

    public void setExpiredTime(String expiredTime) {
        this.expiredTime = expiredTime;
    }

    public String getPriorityRank() {
        return priorityRank;
    }

    public void setPriorityRank(String priorityRank) {
        this.priorityRank = priorityRank;
    }

    public String getPushOrPoll() {
        return pushOrPoll;
    }

    public void setPushOrPoll(String pushOrPoll) {
        this.pushOrPoll = pushOrPoll;
    }

    public String getDelayedTime() {
        return delayedTime;
    }

    public void setDelayedTime(String delayedTime) {
        this.delayedTime = delayedTime;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
