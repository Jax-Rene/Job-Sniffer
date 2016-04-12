package com.zhuangjy.controller;

import com.zhuangjy.util.MD5Util;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by johnny on 16/4/12.
 */
@Controller
@PropertySource(ignoreResourceNotFound = true, value = {"classpath:analysis.properties"})
public class AdminController {
    @Value("${username}")
    private String adminName;
    @Value("${password}")
    private String adminPass;

    @RequestMapping(value = "/admin",method = RequestMethod.POST)
    public String admin(Model model, String userName, String passWord){
        if(MD5Util.MD5(userName).equals(adminName) && MD5Util.MD5(passWord).equals(adminPass))
            return "admin";
        else{
            model.addAttribute("error","密码错误,请重新输入!");
            return "login";
        }
    }
}
