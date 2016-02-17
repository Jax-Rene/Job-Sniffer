package com.zhuangjy.service;

import com.zhuangjy.dao.BaseDao;
import com.zhuangjy.entity.Job;
import com.zhuangjy.worker.LaGouRobotWorker;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;


/**
 * Created by zhuangjy on 2016/2/15.
 */
@Service
public class WorkService {
    private static final Logger LOGGER = LogManager.getLogger(WorkService.class);
    @Autowired
    private BaseDao<Job> baseDao;

    @Value("${lagou.url}")
    private String laGouUrl;
    @Value("${work.type}")
    private String workType;

//    @Scheduled(cron="${scheduled.time}")
    @PostConstruct
    public void grepData(){
        LOGGER.info("start grep data...");
        try {
            for(String job:workType.split(",")){
                new Thread(new LaGouRobotWorker(job,laGouUrl,baseDao)).start();
            }
        } catch (Exception e) {
            LOGGER.error(e);
        }
    }
}
