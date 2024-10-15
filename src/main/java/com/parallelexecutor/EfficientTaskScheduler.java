package com.parallelexecutor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
public class EfficientTaskScheduler {
    private Map<Integer, Integer> dependencyMap;
    private Map<Integer, BaseTask> idToTaskObjectMap;
    private int executedTaskCount;
    private int totalTaskCount;

    public EfficientTaskScheduler(List<BaseTask> taskList){
        executedTaskCount = 0;
        totalTaskCount = taskList.size();
        dependencyMap = new HashMap<>();
        idToTaskObjectMap = new HashMap<>();
        for(BaseTask task: taskList) {
            idToTaskObjectMap.put(task.getId(), task);
            if (!dependencyMap.containsKey(task.getId())) {
                dependencyMap.put(task.getId(), 0);
            }
            for (int parentId : task.getParentTaskIds()) {
                if (!dependencyMap.containsKey(parentId)) {
                    dependencyMap.put(parentId, 0);
                }
                dependencyMap.put(parentId, dependencyMap.get(parentId) + 1);
            }
        }
        System.out.println(dependencyMap);
    }

    public void execute() throws InterruptedException {
        for(int key: dependencyMap.keySet()){
            if(dependencyMap.get(key)==0){
                synchronized (TaskQueueProvider.getSchedulerQueue()) {
                    TaskQueueProvider.getSchedulerQueue().add(idToTaskObjectMap.get(key));
                }
            }
        }
        watchAndSchedule();
    }

    private void watchAndSchedule() throws InterruptedException {
        while(executedTaskCount<totalTaskCount){
            int completedTaskId = -1;
            synchronized (TaskQueueProvider.getAcknowledgementQueue()){
                if(TaskQueueProvider.getAcknowledgementQueue().size()>0) {
                    completedTaskId = TaskQueueProvider.getAcknowledgementQueue().poll();
                }
            }
            if(completedTaskId!=-1){
                executedTaskCount++;
                BaseTask curTask = idToTaskObjectMap.get(completedTaskId);
                for(int parentId: curTask.getParentTaskIds()){
                    dependencyMap.put(parentId, dependencyMap.get(parentId)-1);
                    if(dependencyMap.get(parentId)==0){
                        synchronized (TaskQueueProvider.getAcknowledgementQueue()){
                            TaskQueueProvider.getSchedulerQueue().add(idToTaskObjectMap.get(parentId));
                        }
                    }
                }
            }
        }
    }
}
