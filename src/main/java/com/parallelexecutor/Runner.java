package com.parallelexecutor;

import java.util.ArrayList;
import java.util.List;

public class Runner {
    private static final int totalParallelism = 4;
    public static void main(String [] args) throws InterruptedException {
        for(int i=0;i<totalParallelism;i++){
            TaskExecutor executor = new TaskExecutor(i);
            executor.start();
        }
        List<BaseTask> inputTaskList = new ArrayList<>();
        PrintTask task1 = new PrintTask(1, "Task 1", new ArrayList<>());
        PrintTask task2 = new PrintTask(2, "Task 2", new ArrayList<>());
        PrintTask task3 = new PrintTask(3, "Task 3", List.of(1));
        PrintTask task4 = new PrintTask(4, "Task 4", List.of(1,2));
        PrintTask task5 = new PrintTask(5, "Task 5", List.of(2));
        PrintTask task6 = new PrintTask(6, "Task 6", List.of(4));
        PrintTask task7 = new PrintTask(7, "Task 7", List.of(4));
        PrintTask task8 = new PrintTask(8, "Task 8", List.of(3));
        PrintTask task9 = new PrintTask(9, "Task 9", List.of(5));
        inputTaskList.add(task1);
        inputTaskList.add(task2);
        inputTaskList.add(task3);
        inputTaskList.add(task4);
        inputTaskList.add(task5);
        inputTaskList.add(task6);
        inputTaskList.add(task7);
        inputTaskList.add(task8);
        inputTaskList.add(task9);
        EfficientTaskScheduler taskScheduler = new EfficientTaskScheduler(inputTaskList);
        taskScheduler.execute();
        System.out.println("tasks completed");
    }
}
