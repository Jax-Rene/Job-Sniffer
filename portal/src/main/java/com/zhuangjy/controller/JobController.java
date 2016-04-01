package com.zhuangjy.controller;

import com.zhuangjy.entity.JobAnalysis;
import com.zhuangjy.entity.Origin;
import com.zhuangjy.service.JobService;
import com.zhuangjy.service.OriginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by johnny on 16/3/31.
 */
@RestController
@RequestMapping(value = "/job")
public class JobController {
    @Autowired
    private JobService jobService;
    @Autowired
    private OriginService originService;

    @RequestMapping(value = "/detail",method = RequestMethod.POST)
    public Map<String,Object> detailJob(String job){
        Map<String,Object> map = new HashMap<>();
        List<Origin> origins = originService.currentOrigin();
        long sum = 0l;
        double sumAvg = 0.0;
        for(Origin o:origins){
            sum += o.getCount();
            sumAvg += o.getSalary();
        }
        sumAvg /= origins.size();
        JobAnalysis jobAnalysis = jobService.loadByName(job);
        List<Map<String,Object>> typeCompare = jobService.jobTypeCountCompare(job);
        map.put("job",jobAnalysis);
        map.put("sum",sum);
        map.put("avg",sumAvg);
        map.put("typeCount",typeCompare.get(0));
        map.put("typeSalary",typeCompare.get(1));
        return map;
    }
}
