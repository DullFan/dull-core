package com.dullfan.common.utils.schedule;

import com.dullfan.common.exception.ServiceException;
import com.dullfan.common.utils.StringUtils;
import com.dullfan.common.utils.uuid.IdUtils;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledFuture;

/**
 * 定时任务管理器
 * 1、创建并启动一个定时任务
 * 2、停止一个定时任务
 * 3、更新一个定时任务
 */
@Component
@Slf4j
public class ScheduleManage {

    @Resource
    private ThreadPoolTaskScheduler taskScheduler;

    /**
     * 内部正在执行的定时任务缓存
     */
    private Map<String, ScheduleTaskHolder> cache = new ConcurrentHashMap<>();

    /**
     * 启动一个定时任务
     * @param scheduleTask 定时任务实现类
     * @param cron 定时任务的CRON表达式
     * @return 唯一键
     */
    public String startTask(ScheduleTask scheduleTask, String cron) {
        ScheduledFuture<?> schedule = taskScheduler.schedule(scheduleTask, new CronTrigger(cron));
        String key = IdUtils.fastUUID();
        ScheduleTaskHolder scheduleTaskHolder = new ScheduleTaskHolder(scheduleTask, schedule);
        cache.put(key, scheduleTaskHolder);
        log.info("{}启动成功!key为:{}", scheduleTask.getName(), key);
        return key;
    }

    /**
     * 停止一个定时任务
     * @param key 定时任务唯一标识
     */
    public void stopTask(String key) {
        if (StringUtils.isBlank(key)) {
            return;
        }

        ScheduleTaskHolder scheduleTaskHolder = cache.get(key);
        if (Objects.isNull(scheduleTaskHolder)) {
            return;
        }

        ScheduledFuture scheduledFuture = scheduleTaskHolder.getScheduledFuture();
        boolean cancel = scheduledFuture.cancel(true);
        if (cancel) {
            log.info("{} 停止成功!key为:{}", scheduleTaskHolder.getScheduleTask(), key);
        } else {
            log.info("{} 停止失败!key为:{}", scheduleTaskHolder.getScheduleTask(), key);
        }
    }

    /**
     * 更新定时任务的执行时间
     * @param key 定时任务唯一标识
     * @param cron 新的cron表达式
     * @return 定时任务唯一标识
     */
    public String changeTask(String key,String cron){
        if(StringUtils.isAnyBlank(key,cron)){
            throw new ServiceException(StringUtils.format("定时器唯一标识为空或表达式为空"));
        }
        ScheduleTaskHolder scheduleTaskHolder = cache.get(key);
        if(Objects.isNull(scheduleTaskHolder)){
            throw new ServiceException(StringUtils.format("唯一标识不存在:{}",key));
        }
        stopTask(key);
        return startTask(scheduleTaskHolder.getScheduleTask(),cron);
    }

}
