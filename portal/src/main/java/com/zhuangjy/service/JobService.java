package com.zhuangjy.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zhuangjy.common.JobEnum;
import com.zhuangjy.dao.BaseDao;
import com.zhuangjy.entity.JobAnalysis;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by johnny on 16/3/31.
 */
@Service
public class JobService {
    @Autowired
    private BaseDao<JobAnalysis> baseDao;
    private static ObjectMapper objectMapper = new ObjectMapper();
    private static final Logger LOGGER = LogManager.getLogger(AreaService.class);

    public JobAnalysis loadByName(String job) {
        String hql = "FROM JobAnalysis WHERE jobName=:jobName";
        Map<String, Object> hs = new HashMap<>();
        hs.put("jobName", job);
        return (JobAnalysis) baseDao.uniqueResult(hql, hs);
    }

    public List<Map<String, Object>> jobTypeCountCompare(String job) {
        int index = JobEnum.getTypeByName(job);
        Map<String, Object> countMap = new HashMap<>();
        Map<String, Object> salaryMap = new HashMap<>();
        for (String s : JobEnum.listJobName(index)) {
            JobAnalysis jobAnalysis = loadByName(s);
            if (jobAnalysis != null) {
                countMap.put(s, jobAnalysis.getCount());
                salaryMap.put(s, jobAnalysis.getAvgSalary());
            }
        }
        List<Map<String, Object>> res = new ArrayList<>();
        res.add(countMap);
        res.add(salaryMap);
        return res;
    }
}
