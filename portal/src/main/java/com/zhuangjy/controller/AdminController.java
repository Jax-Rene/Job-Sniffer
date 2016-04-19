package com.zhuangjy.controller;

import com.zhuangjy.entity.PropertiesMap;
import com.zhuangjy.service.AdminService;
import com.zhuangjy.service.MailService;
import com.zhuangjy.util.ShellUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.Date;

/**
 * Created by johnny on 16/4/12.
 */
@RequestMapping("/admin")
@Controller
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

    @RequestMapping(value = "",method = RequestMethod.POST)
    public String admin(Model model, String userName, String passWord,HttpSession session){
        PropertiesMap properties = adminService.currentConfig();
        if(userName.equalsIgnoreCase(properties.getUserName()) && passWord.equalsIgnoreCase(properties.getPassWord())) {
            session.setAttribute("admin",true);
            return "admin";
        }
        else{
            model.addAttribute("loginError","密码错误,请重新输入!");
            return "login";
        }
    }

    @RequestMapping(value = "/load-properties" , method = RequestMethod.GET)
    @ResponseBody
    public PropertiesMap loadProperties(){
        return adminService.currentConfig();
    }

    @RequestMapping(value = "/update-properties",method = RequestMethod.POST)
    @ResponseBody
    public boolean updateProperties(PropertiesMap p){
        adminService.updateConfig(p);
        return true;
    }

    @RequestMapping(value = "/run" , method = RequestMethod.POST)
    @ResponseBody
    public boolean runService(String service,HttpSession session){
        //防止恶意post
        if(session.getAttribute("admin")==null){
            return false;
        }
        String cmd = null;
        String content = null;
        switch (service){
            case "worker":
                cmd = workerCmd;
                content = "Job-Sniffer 开始执行新一轮的数据爬取 ... " + new Date();
                break;
            case "analysis":
                cmd = analysisCmd;
                content = "Job-Sniffer 开始执行新一轮的数据分析 ... " + new Date();
                break;
        }
        mailService.sendEmails("任务报告",content);
        final String finalCmd = cmd;
        mailService.sendEmails("任务报告", "任务结束,结束时间为: " + new Date());
        new Thread(new Runnable() {
            @Override
            public void run() {
                ShellUtil.runShell4Result(finalCmd);
            }
        }).start();

        return true;
    }
}
