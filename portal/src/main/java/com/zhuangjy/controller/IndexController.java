package com.zhuangjy.controller;

import com.zhuangjy.entity.Area;
import com.zhuangjy.entity.Origin;
import com.zhuangjy.service.AreaService;
import com.zhuangjy.service.OriginService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Created by johnny on 16/2/28.
 */
@Controller
public class IndexController {
    @Autowired
    private AreaService areaService;
    @Autowired
    private OriginService originService;
    private static final Logger LOGGER = LogManager.getLogger(IndexController.class);
    private static ObjectMapper objectMapper = new ObjectMapper();

    @RequestMapping("/index")
    public String index(Model model){
        List<Origin> origins = originService.currentOrigin();
        model.addAttribute("origins",origins);
        return "index";
    }

    @RequestMapping("/login")
    public String admin(){
        return "login";
    }

    @RequestMapping(value = "/login-admin" , method = RequestMethod.POST)
    public String login(String userName,String passWord){
        return null;
    }

    @RequestMapping(value = "/area-index")
    public String areaIndex(Model model){
        List<Area> areas = areaService.currentAreaResult();
        model.addAttribute("areas",areas);
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

}
