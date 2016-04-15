package com.zhuangjy.service;

import com.zhuangjy.common.JobEnum;
import com.zhuangjy.dao.BaseDao;
import com.zhuangjy.entity.PropertiesMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * Created by johnny on 16/4/15.
 */
@Service
public class AdminService {
    @Autowired
    private PropertiesMap propertiesMap;
    @Autowired
    private BaseDao<PropertiesMap> baseDao;

    public PropertiesMap currentConfig() {
        PropertiesMap p = null;
        if (propertiesMap.getId() == null) {
            p = (PropertiesMap) baseDao.loadById(1, PropertiesMap.class);
            propertiesMap.setId(1);
            propertiesMap.setArea(p.getArea());
            propertiesMap.setCompanyType(p.getCompanyType());
            propertiesMap.setEducation(p.getEducation());
            propertiesMap.setFinanceStage(p.getFinanceStage());
            propertiesMap.setTime(p.getTime());
            List<String> list = JobEnum.listAllJobs();
            String jobs = "";
            for(int i=0;i<list.size() -1 ;i++)
                jobs += list.get(i) + ",";
            jobs += list.get(list.size()-1);
            propertiesMap.setJob(jobs);
        }
        return propertiesMap;
    }

    public void updateConfig(PropertiesMap p){
        PropertiesMap currentProperties = setCurrentMap(p);
        baseDao.update(currentProperties);
    }

    public PropertiesMap setCurrentMap(PropertiesMap p){
        propertiesMap.setFinanceStage(p.getFinanceStage());
        propertiesMap.setEducation(p.getEducation());
        propertiesMap.setTime(p.getTime());
        propertiesMap.setCompanyType(p.getCompanyType());
        propertiesMap.setArea(p.getArea());
        propertiesMap.setJob(p.getJob());
        return propertiesMap;
    }
}
