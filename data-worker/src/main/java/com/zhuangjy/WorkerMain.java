package com.zhuangjy;

import com.zhuangjy.framework.config.RestApplicationConfig;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;

/**
 * Created by zhuangjy on 2016/1/8.
 */
public class WorkerMain {
    private static final Logger LOGGER = LogManager.getLogger(WorkerMain.class);

    public static void main(String[] args) throws Exception {
        WorkerMain main = new WorkerMain();
        main.start();
    }

    public void init() throws Exception {
        LOGGER.info("execute init method!");
    }

    public void init(String[] args) throws Exception {
        LOGGER.info("executp'ri'n'ge init(args) method!");
    }

    public void start() throws Exception {
        SpringApplication.run(RestApplicationConfig.class);
    }

    public void stop() throws Exception {
        LOGGER.info("execute stop method!");
    }

    public void destroy() throws Exception {
        LOGGER.info("execute destroy method!");
    }

    public WorkerMain() {
    }
}
