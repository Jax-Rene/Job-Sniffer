package com.zhuangjy.service;

import com.zhuangjy.dao.IBatisDao;
import com.zhuangjy.entity.Job;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * Created by johnny on 16/5/12.
 */
@Service
public class DataReportService {
    @Autowired
    private IBatisDao<Job> iBatisDao;


    public List<Job> loadData(Map<String,Object> hs,Integer start,Integer limit) throws SQLException {
        return iBatisDao.queryForList("jobSql.loadData", hs, start, limit);
    }

    public Long dataCount(Map<String,Object> hs) throws SQLException{
        return iBatisDao.queryForTotal("jobSql.loadData",hs);
    }
}
