package com.zhuangjy.controller;

import com.zhuangjy.framework.config.PropertiesMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

/**
 * Created by johnny on 16/4/12.
 */
@Controller
public class AdminController {
    @Autowired
    private PropertiesMap propertiesMap;
    @RequestMapping(value = "/admin",method = RequestMethod.POST)
    public String admin(Model model, String userName, String passWord,HttpSession session){
        if(userName.equalsIgnoreCase(propertiesMap.getUserName()) && passWord.equalsIgnoreCase(propertiesMap.getPassWord())) {
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
        return propertiesMap;
    }
}
