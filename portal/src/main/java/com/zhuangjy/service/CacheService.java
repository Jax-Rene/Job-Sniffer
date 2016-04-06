package com.zhuangjy.service;

import com.zhuangjy.dao.BaseDao;
import com.zhuangjy.dao.IBatisDao;
import com.zhuangjy.entity.Job;
import com.zhuangjy.entity.Relation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by johnny on 16/4/6.
 */
@Service
public class CacheService {
    @Autowired
    private IBatisDao<Job> iBatisDao;
    private List<Relation> relations = new ArrayList();
    private Logger logger = LoggerFactory.getLogger(CacheService.class);

    @PostConstruct
    public void loadRelationCache() {
        try {
            relations = iBatisDao.queryForList("jobSql.getRelationList");
        } catch (SQLException e) {
            logger.error("",e);
        }
        logger.info("relations 缓存加载完毕... ");
    }

    public List<Relation> getRelationCache(){
        if(relations == null || relations.size() == 0){
            loadRelationCache();
        }
        return relations;
    }

    public Integer getRelationCount(){
        if(relations == null || relations.size() == 0){
            loadRelationCache();
        }
        return relations.size();
    }
}
