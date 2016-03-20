package com.zhuangjy.controller;

import com.zhuangjy.entity.Area;
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
public class AreaController {
    @Autowired
    private AreaService areaService;

    @RequestMapping("/detail/{city}")
    public Area detailAreaInfo(@PathVariable String city){
        return areaService.loadByName(city);
    }
}
