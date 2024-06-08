package org.example.queue;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class MyQueue {

    public static BlockingQueue<String> pushQueue = new ArrayBlockingQueue<>(5); //第二个参数表示对于等待的线程是否需要公平
    public static BlockingQueue<String> pollQueue = new ArrayBlockingQueue<>(5);

    public static void addPush(Object msg){

        try {
            pushQueue.put(((String) msg));
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
    public static String getPush(){
        try {
            return pushQueue.take();
        } catch (InterruptedException e) {
//            System.out.println("队列已空，无法取出");
            Thread.currentThread().interrupt();
            return "默认值";
        }
    }
    public static void addPoll(String msg){
        try {
            pollQueue.put(((String) msg));
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
    public static String getPoll(){
        try {
            return pollQueue.take();
        } catch (InterruptedException e) {
//            System.out.println("队列已空，无法取出");
            Thread.currentThread().interrupt();
            return "默认值";
        }
    }
}
