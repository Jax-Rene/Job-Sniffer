package com.zhuangjy.controller;

import com.zhuangjy.entity.PropertiesMap;
import com.zhuangjy.service.AdminService;
import com.zhuangjy.service.MailService;
import com.zhuangjy.util.ShellUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.SystemEnvironmentPropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.sql.SQLException;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Created by johnny on 16/4/12.
 */
@RequestMapping("/admin")
@RestController
@PropertySource(value = {"file:/Users/johnny/Desktop/JobsAnalysis/analysis.properties"})
@Configuration
public class AdminController {
    @Value("${worker_cmd}")
    private String workerCmd;
    @Value("${analysis_cmd}")
    private String analysisCmd;

    @Autowired
    private AdminService adminService;
    @Autowired
    private MailService mailService;

    @RequestMapping(value = "/load-properties" , method = RequestMethod.GET)
    public PropertiesMap loadProperties(){
        return adminService.currentConfig();
    }

    @RequestMapping(value = "/update-properties",method = RequestMethod.POST)
    public boolean updateProperties(PropertiesMap p){
        adminService.updateConfig(p);
        adminService.installCrontab(p.getTime(),workerCmd,analysisCmd);
        return true;
    }

    @RequestMapping(value = "/run" , method = RequestMethod.POST)
    public boolean runService(String service) throws SQLException{
        String cmd = null;
        String content = null;
        switch (service){
            case "worker":
                cmd = workerCmd;
                content = "[Job-Sniffer] 开始执行新一轮的数据爬取: " + new Date();
                break;
            case "analysis":
                cmd = analysisCmd;
                adminService.deleteAnalysisData();
                content = "[Job-Sniffer] 开始执行新一轮的数据分析: " + new Date();
                break;
        }
        mailService.sendEmails("任务报告",content);
        final String finalCmd = cmd;
        new Thread(new Runnable() {
            @Override
            public void run() {
                ShellUtil.runShell4Result(finalCmd);
                mailService.sendEmails("任务报告", "任务结束,结束时间为: " + new Date());
            }
        }).start();
        return true;
    }
}
