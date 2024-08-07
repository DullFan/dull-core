package com.dullfan.common.utils.schedule;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.concurrent.ScheduledFuture;

/**
 * 定时任务和定时任务结果的缓存对象
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleTaskHolder implements Serializable {

    /**
     * 定时任务实体
     */
    private ScheduleTask scheduleTask;

    /**
     * 执行任务的结果
     */
    private ScheduledFuture scheduledFuture;

}
