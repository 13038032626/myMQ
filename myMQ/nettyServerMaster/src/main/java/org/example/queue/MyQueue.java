package org.example.queue;

import java.util.HashMap;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;

public class MyQueue {

    ConcurrentHashMap<String, Queue<?>> queues = new ConcurrentHashMap<>();

    public void createQueue(){
        
    }
}
