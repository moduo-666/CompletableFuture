package org.example.service.impl;

import org.example.config.CustomizeRejectedExecutionHandler;
import org.example.service.SpecificTaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class SpecificTaskServiceImpl implements SpecificTaskService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SpecificTaskServiceImpl.class);

    @Override
    public String specificTask(String threadName, Integer type) {
        String name = Thread.currentThread().getName();
        long id = Thread.currentThread().getId();
        LOGGER.info("当前线程名称：{}, 调用线程名称：{}, 当前线程id:{}, 是否是自定义线程：{}" ,name, threadName, id, type==1 ? "否" : "是");
//        throw new RuntimeException("手动抛出异常");
        return "当前线程名称：" + name + ",当前线程id:" + id;
    }

    @Override
    public String specificTaskByException() {
        String name = Thread.currentThread().getName();
        long id = Thread.currentThread().getId();
        LOGGER.info("当前线程名称：{}, 当前线程id:{}" ,name, id);
        throw new RuntimeException("手动抛出异常");
//        return "当前线程名称：" + name + ",当前线程id:" + id;
    }
}
