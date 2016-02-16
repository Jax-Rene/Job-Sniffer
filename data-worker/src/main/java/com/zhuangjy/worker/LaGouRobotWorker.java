package com.zhuangjy.worker;

import com.zhuangjy.dao.BaseDao;
import com.zhuangjy.entity.Job;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.*;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

/**
 * Created by zhuangjy on 2016/1/11.
 */
public class LaGouRobotWorker extends BaseWorker{
    public LaGouRobotWorker(String jobName, String url,BaseDao baseDao) {
        super(jobName,baseDao);
        this.url = constructUrl(url);
    }

    @Override
    public String constructUrl(String orginUrl) {
        try {
            return orginUrl.replace("kd=","kd=" + URLEncoder.encode(jobName,"utf-8"));
        } catch (UnsupportedEncodingException e) {
            LOGGER.error(e);
        }
        return  null;
    }


    @Override
    public void run() {
        try {
            LOGGER.info("Current LaGouThread: " + jobName);
            Map<String, Map<String, Map<String, Object>>> maps = null;
            while (true){
                int i = 1;
                Document doc = Jsoup.connect(url + i++).ignoreContentType(true).timeout(1000000).get();
                Element body = doc.body();
                try {
                    maps = objectMapper.readValue(body.text(), Map.class);
                } catch (IOException e) {
                    e.printStackTrace();
                    continue;
                }
                //如果result为空退出循环
                List<Map<String, Object>> result = (List<Map<String, Object>>) maps.get("content").get("result");
                if(result.isEmpty())
                    break;
                for (Map<String, Object> map : result) {
                    //筛选不是今年的 以及不是全职的
                    if (calendar.after(map.get("createTime")) || !map.get("jobNature").equals("全职"))
                        continue;
                    String companyCity = (String) map.get("city");
                    String companyName = (String) map.get("companyName");
                    String workYear = (String) map.get("workYear");
                    String salary = (String) map.get("salary");
                    String education = (String) map.get("education");
                    String financeStage = (String) map.get("financeStage");
                    String industryField = (String) map.get("industryField");
                    String companySize = (String) map.get("companySize");
                    String positionName = (String) map.get("positionName");
                    Job job = new Job(positionName, companyCity, companyName, calcAvg(workYear), calcAvg(salary), education, financeStage, industryField, calcAvg(companySize));
                    baseDao.save(job);
                }
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
    }
}
