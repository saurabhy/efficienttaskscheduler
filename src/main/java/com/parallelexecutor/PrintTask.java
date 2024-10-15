package com.parallelexecutor;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class PrintTask extends BaseTask{

    public PrintTask(int i, String s, List<Integer> integers) {
        super(i, s, integers);
    }

    @Override
    public void process() {
        System.out.println("Running Task with Id "+ getId());
    }
}
