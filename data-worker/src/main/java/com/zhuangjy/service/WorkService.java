package com.zhuangjy.service;

import com.zhuangjy.common.JobEnum;
import com.zhuangjy.dao.BaseDao;
import com.zhuangjy.entity.Job;
import com.zhuangjy.framework.utils.ShellUtil;
import com.zhuangjy.worker.LaGouRobotWorker;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.lang.management.ManagementFactory;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;


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

    @PostConstruct
    public void grepData() {
        LOGGER.info("start grep data...");
        try {
            List<Thread> list = new ArrayList<>();
            for (String job : JobEnum.listAllJobs()) {
                Thread thread = new Thread(new LaGouRobotWorker(job, laGouUrl, baseDao));
                list.add(thread);
                thread.start();
            }
            //每一分钟检测一次是否全部运行完成
            while (true) {
                TimeUnit.MINUTES.sleep(1);
                boolean flag = true;
                for (Thread s : list) {
                    if (s.isAlive()) {
                        flag = false;
                        break;
                    }
                }
                if (flag)
                    ShellUtil.runShell("kill -9 " + ManagementFactory.getRuntimeMXBean().getName().split("@")[0]);
            }
        } catch (Exception e) {
            LOGGER.error(e);
        }
    }
}
