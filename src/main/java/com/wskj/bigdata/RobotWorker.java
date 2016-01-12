package com.wskj.bigdata;

import com.wskj.bigdata.bean.JobConfig;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.ObjectMapper;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by zhuangjy on 2016/1/11.
 */
public class RobotWorker implements Runnable {
    private static ObjectMapper objectMapper = new ObjectMapper();
    private static Calendar calendar = Calendar.getInstance();

    static {
        calendar.set(2016, 01, 01);
        objectMapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
    }

    private String url;
    private String job;
    private String city;
    private String fileSrc;

    public RobotWorker(String job, String city, String fileSrc) {
        this.job = job;
        this.city = city;
        this.fileSrc = fileSrc;
        url = "http://120.132.69.172/jobs/positionAjax.json?city=" + city + "&kd=" + job + "&pn=";
    }

    @Override
    public void run() {
        try {
            System.out.println("当前搜索 " + job + "线程");
            int totalPage = getPageCount();
            Map<String, Map<String, Map<String, Object>>> maps = null;
            List<JobConfig> list = new ArrayList<>();
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
                    JobConfig jobConfig = new JobConfig(job, companyCity, companyName, calcAvg(workYear), calcAvg(salary), education, financeStage, industryField, calcAvg(companySize));
                    list.add(jobConfig);
                }
                write(list);
                System.out.println("读取完第" + i + "页 一共有 " + totalPage + "页");
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public Integer getPageCount() throws Exception {
        Document doc = Jsoup.connect(url + 0).ignoreContentType(true).timeout(1000000).get();
        Element body = doc.body();
        Map<String, Map<String, Object>> maps = objectMapper.readValue(body.text(), Map.class);
        return (Integer) maps.get("content").get("totalPageCount");
    }

    public void write(List<JobConfig> list) throws Exception {
        File file = new File(fileSrc);
        BufferedWriter writer = new BufferedWriter(new FileWriter(file, true));
        for (JobConfig jobConfig : list) {
            writer.write(jobConfig.toString());
        }
    }

    /**
     * 求一个字符串中所有数字的平均值
     */
    public float calcAvg(String str){
        Pattern pattern = Pattern.compile("(\\d+)");
        Matcher matcher = pattern.matcher(str);
        float avg = 0.0f;
        int i = 0;
        while (matcher.find()){
            i++;
            avg += Float.parseFloat(matcher.group());
        }
        if(i!=0)
            return avg / i;
        else
            return 0.0f;
    }
}
