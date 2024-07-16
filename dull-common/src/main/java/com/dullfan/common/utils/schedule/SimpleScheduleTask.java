package com.dullfan.common.utils.schedule;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class SimpleScheduleTask implements ScheduleTask{
    @Override
    public String getName() {
        return "测试定时任务";
    }

    @Override
    public void run() {
        log.info(getName()+"正在执行");
    }
}
