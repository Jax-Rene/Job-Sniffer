package com.zhuangjy.service;

import com.zhuangjy.dao.BaseDao;
import com.zhuangjy.entity.Origin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by johnny on 16/3/8.
 */
@Service
public class OriginService {
    @Autowired
    private BaseDao<Origin> baseDao;

    public List<Origin> currentOrigin(){
        String hql = "FROM Origin";
        return baseDao.query(hql);
    }

    public Origin loadOrigin(Integer id){
        return (Origin) baseDao.loadById(id,Origin.class);
    }
}
