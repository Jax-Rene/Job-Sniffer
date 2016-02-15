package com.zhuangjy.worker;

import com.zhuangjy.dao.BaseDao;
import com.zhuangjy.entity.Job;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.ObjectMapper;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by zhuangjy on 2016/2/15.
 */
public abstract class BaseWorker implements Runnable {
    protected static final Logger LOGGER = LogManager.getLogger(BaseWorker.class);
    protected static ObjectMapper objectMapper = new ObjectMapper();
    protected static Calendar calendar = Calendar.getInstance();
    protected BaseDao baseDao;

    static {
        calendar.set(2016, 01, 01);
        objectMapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
    }

    protected String url;
    protected String jobName;

    public BaseWorker(String jobName, BaseDao baseDao) {
        this.jobName = jobName;
        this.baseDao = baseDao;
    }

    //构造动态构造url
    public abstract String constructUrl(String orginUrl);

    @Override
    public void run() {
        try {
            LOGGER.info("Current Thread: " + jobName);
            int totalPage = getPageCount();
            Map<String, Map<String, Map<String, Object>>> maps = null;
            for (int i = 1; i <= totalPage; i++) {
                Document doc = Jsoup.connect(url + i).ignoreContentType(true).timeout(1000000).get();
                Element body = doc.body();
                try {
                    maps = objectMapper.readValue(body.text(), Map.class);
                } catch (IOException e) {
                    e.printStackTrace();
                    continue;
                }
                List<Map<String, Object>> result = (List<Map<String, Object>>) maps.get("content").get("result");
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
                    Job job = new Job(jobName, companyCity, companyName, calcAvg(workYear), calcAvg(salary), education, financeStage, industryField, calcAvg(companySize));
                    baseDao.save(job);
                }
                LOGGER.debug("读取完第" + i + "页 一共有 " + totalPage + "页");
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }

    }

    /**
     * 获取总页数
     *
     * @return
     * @throws Exception
     */
    public abstract Integer getPageCount() throws IOException;


    /**
     * 求一个字符串中所有数字的平均值
     */
    public Float calcAvg(String str) {
        Pattern pattern = Pattern.compile("(\\d+)");
        Matcher matcher = pattern.matcher(str);
        float avg = 0.0f;
        int i = 0;
        while (matcher.find()) {
            i++;
            avg += Float.parseFloat(matcher.group());
        }
        if (i != 0)
            return avg / i;
        else
            return 0.0f;
    }
}
