package com.zhuangjy.controller;

import com.zhuangjy.entity.Relation;
import com.zhuangjy.service.CacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by johnny on 16/4/6.
 */
@RestController
@RequestMapping(value = "/relation")
public class RelationController {
    @Autowired
    private CacheService cacheService;

    @RequestMapping(value = "/whole/{count}")
    public List<Relation> loadRelation(@PathVariable Integer count){
        List<Relation> relations = cacheService.getRelationCache();
        if(count > relations.size())
            return null;
        return relations.subList(0,count);
    }
}
