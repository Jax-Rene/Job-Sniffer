package com.zhuangjy.service;

import com.zhuangjy.common.JobType;
import com.zhuangjy.dao.BaseDao;
import com.zhuangjy.entity.Area;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by johnny on 16/3/1.
 */
@Service
public class AreaService {
    @Autowired
    private BaseDao<Area> baseDao;
    private static ObjectMapper objectMapper = new ObjectMapper();
    private static final Logger LOGGER = LogManager.getLogger(AreaService.class);
    public List<Area> currentAreaResult(){
        String hql = "FROM Area";
        return baseDao.query(hql);
    }

    public Area loadByName(String city){
        String hql = "FROM Area Where area=:area";
        Map<String,Object> hs = new HashMap<>();
        hs.put("area",city);
        return (Area) baseDao.uniqueResult(hql,hs);
    }

    public String mapJobType(String jobType) {
        String s = "";
        try {
            Map<String, Object> map = objectMapper.readValue(jobType, Map.class);
            Map<String, Object> res = new HashMap<>();
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                res.put(JobType.getJobTypeName(Integer.parseInt(entry.getKey())), entry.getValue());
            }
            StringWriter stringWriter = new StringWriter();
            objectMapper.writeValue(stringWriter, res);
            s = stringWriter.toString();
        }catch (IOException e){
            LOGGER.error("IOException",e);
        }
        return s;
    }
}
