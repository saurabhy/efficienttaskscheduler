package com.parallelexecutor;

import java.util.LinkedList;
import java.util.Queue;

public class TaskQueueProvider {
    private static Queue<BaseTask> schedulerQueue = new LinkedList<>();
    private static Queue<Integer> acknowledgementQueue = new LinkedList<>();

    public static Queue<BaseTask> getSchedulerQueue(){
        return schedulerQueue;
    }

    public static Queue<Integer> getAcknowledgementQueue(){
        return acknowledgementQueue;
    }

}
