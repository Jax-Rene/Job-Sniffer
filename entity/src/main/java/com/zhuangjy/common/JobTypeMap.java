package com.zhuangjy.common;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by johnny on 16/3/27.
 */
public class JobTypeMap {
    private static Map<Integer,String> jobTypeMap = new HashMap<>();
    static {
        jobTypeMap.put(1,"后端开发");
        jobTypeMap.put(2,"移动开发");
        jobTypeMap.put(3,"前端开发");
        jobTypeMap.put(4,"测试");
        jobTypeMap.put(5,"运维");
        jobTypeMap.put(6,"DBA");
        jobTypeMap.put(7,"项目");
        jobTypeMap.put(8,"硬件");
        jobTypeMap.put(9,"企业");
    }

    public static String getJobTypeName(int index){
        return jobTypeMap.get(index);
    }
}
