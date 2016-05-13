package com.zhuangjy.controller;

import com.zhuangjy.common.JobType;
import com.zhuangjy.dao.IBatisDao;
import com.zhuangjy.entity.Job;
import com.zhuangjy.service.DataReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by johnny on 16/5/11.
 */
@RestController
@RequestMapping("/data-report")
public class DataReportController {
    @Autowired
    private DataReportService dataReportService;

    @RequestMapping(value = "/load", method = RequestMethod.POST)
    public Map<String, Object> load(Integer start, Integer limit, String jobName, String jobType, String city, String education,
                          String financeStage, String industryField, String workStart, String workEnd, String startTime, String endTime) throws IOException, SQLException {
        Map<String,Object> res = new HashMap<>();
        Map<String, Object> hs = new HashMap<>();
        hs.put("jobName", jobName);
        hs.put("jobType", jobType);
        hs.put("city", city);
        hs.put("education", education);
        hs.put("financeStage", financeStage);
        hs.put("industryField", industryField);
        hs.put("workStart", workStart);
        hs.put("workEnd", workEnd);
        hs.put("startTime", startTime);
        hs.put("endTime", endTime);
        List<Job> list =  dataReportService.loadData(hs,start,limit);
        Long count = dataReportService.dataCount(hs);
        for(Job j: list)
            j.setJobTypeName(JobType.getJobTypeName(j.getJobType()));
        res.put("records",list);
        res.put("totalCount",count);
        return res;
    }
}
