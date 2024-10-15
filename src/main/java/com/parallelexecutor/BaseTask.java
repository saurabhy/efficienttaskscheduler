package com.parallelexecutor;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class BaseTask {
    private int id;
    private String description;
    private List<Integer> parentTaskIds;
    public abstract  void process();
}
