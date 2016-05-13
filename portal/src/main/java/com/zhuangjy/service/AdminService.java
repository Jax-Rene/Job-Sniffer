package com.zhuangjy.service;

import com.zhuangjy.common.JobEnum;
import com.zhuangjy.dao.BaseDao;
import com.zhuangjy.dao.IBatisDao;
import com.zhuangjy.entity.Job;
import com.zhuangjy.entity.PropertiesMap;
import com.zhuangjy.util.ShellUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.print.attribute.standard.SheetCollate;
import java.sql.SQLException;
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
    @Autowired
    private IBatisDao<Job> iBatisDao;

    public PropertiesMap currentConfig() {
        if (propertiesMap.getId() == null) {
            PropertiesMap p = (PropertiesMap) baseDao.loadById(1, PropertiesMap.class);
            propertiesMap.setId(1);
            propertiesMap.setArea(p.getArea());
            propertiesMap.setCompanyType(p.getCompanyType());
            propertiesMap.setEducation(p.getEducation());
            propertiesMap.setFinanceStage(p.getFinanceStage());
            propertiesMap.setTime(p.getTime());
            List<String> list = JobEnum.listAllJobs();
            String jobs = "";
            for (int i = 0; i < list.size() - 1; i++)
                jobs += list.get(i) + ",";
            jobs += list.get(list.size() - 1);
            propertiesMap.setJob(jobs);
        }
        return propertiesMap;
    }

    public void updateConfig(PropertiesMap p) {
        PropertiesMap currentProperties = setCurrentMap(p);
        baseDao.update(currentProperties);
    }

    public PropertiesMap setCurrentMap(PropertiesMap p) {
        propertiesMap.setFinanceStage(p.getFinanceStage());
        propertiesMap.setEducation(p.getEducation());
        propertiesMap.setTime(p.getTime());
        propertiesMap.setCompanyType(p.getCompanyType());
        propertiesMap.setArea(p.getArea());
        propertiesMap.setJob(p.getJob());
        return propertiesMap;
    }


    public void deleteAnalysisData() throws SQLException {
        iBatisDao.delete("jobSql.deleteAreaAnalysis");
        iBatisDao.delete("jobSql.deleteJobAnalysis");
        iBatisDao.delete("jobSql.deleteOrigin");
    }

    public void installCrontab(String cron,String workerCmd,String analysisCmd){
        String cmd = cron + " " + workerCmd + ";" + analysisCmd;
        String c = "(crontab -l 2>/dev/null | grep -Fv data;echo \"" + cmd + "\") | crontab - ";
        System.out.println(c);
        ShellUtil.runShell(c);
    }
}
