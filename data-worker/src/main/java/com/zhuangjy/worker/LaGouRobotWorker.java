package com.zhuangjy.worker;

import com.zhuangjy.common.JobEnum;
import com.zhuangjy.common.JobTypeMap;
import org.codehaus.jackson.JsonParseException;
import com.zhuangjy.common.JobType;
import com.zhuangjy.dao.BaseDao;
import com.zhuangjy.entity.Job;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.*;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Created by zhuangjy on 2016/1/11.
 */
public class LaGouRobotWorker extends BaseWorker {

    private int jobType = 0;
    private int awaitTime = 0;
    private String origin = "拉勾网";

    public LaGouRobotWorker(String jobName, String url, BaseDao baseDao) {
        super(jobName, baseDao);
        this.url = constructUrl(url);
        this.jobType = JobEnum.getTypeByName(jobName);
    }

    @Override
    public String constructUrl(String orginUrl) {
        try {
            return orginUrl.replace("kd=", "kd=" + URLEncoder.encode(jobName, "utf-8"));
        } catch (UnsupportedEncodingException e) {
            LOGGER.error(e);
        }
        return null;
    }


    @Override
    public void run() {
        LOGGER.info("Current LaGouThread: " + jobName);
        Map<String, Map<String, Map<String, Object>>> maps = null;
        int i = 1;
        while (true) {
            Document doc;
            String dest = null;
            try {
                dest = url + i;
                doc = Jsoup.connect(dest).ignoreContentType(true).timeout(100000).get();
                Element body = doc.body();
                maps = objectMapper.readValue(body.text(), Map.class);
                //如果result为空退出循环
                List<Map<String, Object>> result = (List<Map<String, Object>>) maps.get("content").get("result");
                if (result.isEmpty())
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
                    Job job = new Job(positionName, jobType, companyCity, companyName, calcAvg(workYear), calcAvg(salary), education, financeStage, industryField, calcAvg(companySize),origin);
                    baseDao.save(job);
                }
                i++;
                awaitTime = 0;
                //降低获取频率防止屏蔽
                TimeUnit.SECONDS.sleep(3);
            } catch (JsonParseException e) {
                LOGGER.error("解析错误,跳过: ", e);
                i++;
                awaitTime = 0;
            } catch (Exception e) {
                awaitTime++;
                //如果连续失败5次直接该条
                if (awaitTime == 5) {
                    LOGGER.error("失败超过5次,跳过url: " + dest);
                    awaitTime = 0;
                    i++;
                }
                LOGGER.error("当前失败次数: " + awaitTime + "\n", e);
            }
        }

        LOGGER.info(jobName + "线程grep数据完毕!");
    }
}
