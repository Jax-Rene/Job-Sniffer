package com.zhuangjy.worker;

import com.zhuangjy.dao.BaseDao;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.ObjectMapper;

import java.util.Calendar;
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
