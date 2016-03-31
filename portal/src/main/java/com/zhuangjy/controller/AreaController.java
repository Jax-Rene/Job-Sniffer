package com.zhuangjy.controller;

import com.zhuangjy.entity.AreaAnalysis;
import com.zhuangjy.service.AreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by johnny on 16/3/20.
 */
@RestController
@RequestMapping(value = "/area")
public class AreaController{
    @Autowired
    private AreaService areaService;

    @RequestMapping("/detail/{city}")
    public AreaAnalysis detailAreaInfo(@PathVariable String city) {
        AreaAnalysis areaAnalysis = areaService.loadByName(city);
        if(areaAnalysis == null)
            return null;
        areaAnalysis.setJobTypeCount(areaService.mapJobType(areaAnalysis.getJobTypeCount()));
        areaAnalysis.setJobTypeSalary(areaService.mapJobType(areaAnalysis.getJobTypeSalary()));
        return areaAnalysis;
    }
}
