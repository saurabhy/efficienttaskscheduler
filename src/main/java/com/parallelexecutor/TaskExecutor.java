package com.parallelexecutor;

public class TaskExecutor extends Thread{
    private int id;
    public TaskExecutor(int id){
        this.id = id;
    }
    @Override
    public void run(){
        while(true){
            BaseTask task = null;
            synchronized (TaskQueueProvider.getSchedulerQueue()){
                if(TaskQueueProvider.getSchedulerQueue().size()!=0) {
                    task = TaskQueueProvider.getSchedulerQueue().poll();
                }
            }
            if(task!=null){
                task.process();
               synchronized (TaskQueueProvider.getAcknowledgementQueue()) {
                    TaskQueueProvider.getAcknowledgementQueue().add(task.getId());
               }
            }
        }
    }
}
