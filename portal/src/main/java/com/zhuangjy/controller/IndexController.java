package com.zhuangjy.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zhuangjy.common.JobTypeMap;
import com.zhuangjy.entity.AreaAnalysis;
import com.zhuangjy.entity.Mail;
import com.zhuangjy.entity.Origin;
import com.zhuangjy.entity.PropertiesMap;
import com.zhuangjy.service.*;
import org.apache.catalina.startup.PasswdUserDatabase;
import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by johnny on 16/2/28.
 */
@Controller
public class IndexController {
    @Autowired
    private AdminService adminService;
    @Autowired
    private AreaService areaService;
    @Autowired
    private OriginService originService;
    @Autowired
    private CacheService cacheService;
    @Autowired
    private MailService mailService;
    private static final Logger LOGGER = LogManager.getLogger(IndexController.class);
    private static ObjectMapper objectMapper = new ObjectMapper();

    @RequestMapping("/index")
    public String index(Model model) throws SQLException {
        List<Origin> origins = originService.currentOrigin();
        model.addAttribute("origins", origins);
        model.addAttribute("config", adminService.currentConfig());
        model.addAttribute("time", cacheService.getLastDate());
        return "index";
    }

    @RequestMapping("/login")
    public String login(String error, Model model) {
        if (error != null)
            model.addAttribute("loginError", "身份验证失败,请重新登录");
        return "login";
    }

    @RequestMapping(value = "/area-index")
    public String areaIndex(Model model) {
        List<AreaAnalysis> areaAnalysises = areaService.currentAreaResult();
        model.addAttribute("areas", areaAnalysises);
        return "area-index";
    }

    @RequestMapping(value = "/load-origin", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> loadOrigin(Integer id) {
        Origin origin = originService.loadOrigin(id);
        try {
            Map<String, Object> map = objectMapper.readValue(origin.getDetailCount(), Map.class);
            return map;
        } catch (IOException e) {
            LOGGER.error(e);
            return null;
        }
    }

    @RequestMapping("/admin")
    public String admin() {
        return "admin";
    }


    @RequestMapping("/job-index")
    public String jobIndex() {
        return "job-index";
    }

    @RequestMapping("/relation-index")
    public String relationIndex(Model model) {
        Integer count = cacheService.getRelationCount();
        model.addAttribute("count", count);
        return "relation-index";
    }

    @RequestMapping(value = "/subscribe", method = RequestMethod.POST)
    @ResponseBody
    public boolean subscribe(Mail mail) {
        mailService.subscribe(mail);
        return true;
    }

    @RequestMapping(value = "/unsubscribe", method = RequestMethod.GET)
    @ResponseBody
    public String unsubscribe(String mail) {
        mailService.unSubscribe(mail);
        return "解除订阅购成功!";
    }

    @RequestMapping(value = "/data-report", method = RequestMethod.GET)
    public String dataReport() {
        return "data-report";
    }

    @RequestMapping(value = "/data-report-iframe", method = RequestMethod.GET)
    public String iframe(Model model) {
        model.addAttribute("jobTypeMap", JobTypeMap.getJobTypeMap());
        PropertiesMap map = adminService.currentConfig();
        List<String> area = splitString(map.getArea());
        List<String> education = splitString(map.getEducation());
        List<String> financeStage = splitString(map.getFinanceStage());
        List<String> industryField = splitString(map.getCompanyType());
        model.addAttribute("city",area);
        model.addAttribute("education",education);
        model.addAttribute("jobType",JobTypeMap.getJobTypeMap());
        model.addAttribute("financeStage",financeStage);
        model.addAttribute("industryField",industryField);
        return "data-iframe";
    }


    public List<String> splitString(String str) {
        List<String> list = new ArrayList<>();
        if (StringUtils.isNotBlank(str)) {
            for (String s : str.split(",")) {
                list.add(s);
            }
        }
        return list;
    }


}
