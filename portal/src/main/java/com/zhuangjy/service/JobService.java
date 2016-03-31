package com.zhuangjy.service;

import com.zhuangjy.dao.BaseDao;
import com.zhuangjy.entity.JobAnalysis;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Created by johnny on 16/3/31.
 */
@Service
public class JobService {
    private BaseDao<JobAnalysis> baseDao;
    private static ObjectMapper objectMapper = new ObjectMapper();
    private static final Logger LOGGER = LogManager.getLogger(AreaService.class);
}
