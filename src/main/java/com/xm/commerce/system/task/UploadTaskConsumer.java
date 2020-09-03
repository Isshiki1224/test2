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

    @Scheduled(fixedRate = 8000)
    public void run() throws Exception {
        Object task;
        log.info("active pool count " + executor.getActiveCount());
        log.info("core pool size " + executor.getThreadPoolExecutor().getQueue().remainingCapacity());
        try {
            task = redisTemplate.opsForList().leftPop(RedisConstant.UPLOAD_TASK_LIST_KEY, 5, TimeUnit.SECONDS);
            if (task == null) {
                return;
            }
        } catch (Exception e) {
            return;
        }
        log.info("get from redis: " + task);
        if (executor.getThreadPoolExecutor().getQueue().remainingCapacity() > 0) {
            uploadTask.taskExecute(task);
        }
    }

}
