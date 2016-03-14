package com.zhuangjy.service;

import com.zhuangjy.dao.BaseDao;
import com.zhuangjy.entity.Area;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by johnny on 16/3/1.
 */
@Service
public class AreaService {
    @Autowired
    private BaseDao<Area> baseDao;

    public List<Area> currentAreaResult(){
        String hql = "FROM Area";
        return baseDao.query(hql);
    }
}
