package com.zhuangjy.common;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhuangjy on 2016/1/8.
 */
public enum JobName {
    JAVA("java"),IOS("IOS"),ANDROID("android"),PHP("PHP"),C("C++"),TEST("测试"),FONT("前端开发"),PYTHON("python"),
    SCALA("scala"),ASP(".net"),NODE("Node.js"),Go("go"),DM("数据挖掘"),HTML5("HTML5"),WP("WP"),OM("运维"),
    PERL("perl"),RUBY("ruby"),HADOOP("hadoop"),ALGO("算法"),COCOS2D("COCOS2D-X"),DBA("DBA"),SAFE("安全"),QIANRU("嵌入式");

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    JobName(String name) {
        this.name = name;
    }

    public static List<String> returnAllJobName(){
        List<String> jobNames = new ArrayList<String>();
        for(JobName jobName:JobName.values()){
            jobNames.add(jobName.getName());
        }
        return jobNames;
    }
}
