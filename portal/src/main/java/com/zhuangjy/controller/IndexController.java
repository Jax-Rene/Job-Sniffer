package com.zhuangjy.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zhuangjy.entity.AreaAnalysis;
import com.zhuangjy.entity.Origin;
import com.zhuangjy.entity.PropertiesMap;
import com.zhuangjy.service.AdminService;
import com.zhuangjy.service.AreaService;
import com.zhuangjy.service.CacheService;
import com.zhuangjy.service.OriginService;
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
    private static final Logger LOGGER = LogManager.getLogger(IndexController.class);
    private static ObjectMapper objectMapper = new ObjectMapper();

    @RequestMapping("/index")
    public String index(Model model){
        List<Origin> origins = originService.currentOrigin();
        model.addAttribute("origins",origins);
        model.addAttribute("config",adminService.currentConfig());
        return "index";
    }

    @RequestMapping("/login")
    public String login(HttpSession session){
        if(session.getAttribute("admin") != null)
            return "admin";
        return "login";
    }

    @RequestMapping(value = "/area-index")
    public String areaIndex(Model model){
        List<AreaAnalysis> areaAnalysises = areaService.currentAreaResult();
        model.addAttribute("areas", areaAnalysises);
        return "area-index";
    }

    @RequestMapping(value = "/load-origin",method = RequestMethod.GET)
    @ResponseBody
    public Map<String,Object> loadOrigin(Integer id){
        Origin origin = originService.loadOrigin(id);
        try {
            Map<String,Object> map = objectMapper.readValue(origin.getDetailCount(),Map.class);
            return map;
        } catch (IOException e) {
            LOGGER.error(e);
            return null;
        }
    }

    @RequestMapping("/job-index")
    public String jobIndex(){
        return "job-index";
    }

    @RequestMapping("/relation-index")
    public String relationIndex(Model model){
        Integer count = cacheService.getRelationCount();
        model.addAttribute("count",count);
        return "relation-index";
    }
}
