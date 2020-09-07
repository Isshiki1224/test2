package com.xm.commerce.system.task;

import com.xm.commerce.system.constant.RedisConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
@EnableScheduling
public class UploadTaskConsumer {

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Resource
    private UploadTask uploadTask;

    @Resource(name = "taskExecutor")
    private ThreadPoolTaskExecutor executor;

    @Scheduled(fixedRate = 2000)
    public void run() throws Exception {
        if (executor.getThreadPoolExecutor().getQueue().remainingCapacity() > 20) {
            Object task;
            log.info("active pool count " + executor.getActiveCount());
            log.info("core pool size " + executor.getThreadPoolExecutor().getQueue().remainingCapacity());
            task = redisTemplate.opsForList().leftPop(RedisConstant.UPLOAD_TASK_LIST_KEY);
            if (task == null) {
                return;
            }
            log.info("task: {}", task);
            log.info("get from redis: " + task);
            uploadTask.taskExecute(task);
        }
    }

}
