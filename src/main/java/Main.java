import com.wskj.bigdata.bean.JobConfig;
import com.wskj.bigdata.common.JobName;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.ObjectMapper;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

/**
 * Created by zhuangjy on 2016/1/8.
 */
public class Main {
    private static ObjectMapper objectMapper = new ObjectMapper();
    private static Calendar calendar = Calendar.getInstance();

    static {
        calendar.set(2016, 01, 01);
        objectMapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
    }

    public static void main(String[] args) {
        try {
            String city = "";
            List<String> jobs = JobName.returnAllJobName();
            Map<String, Integer> pageNum = new HashMap<>();
            for (String jobName : jobs) {
                pageNum.put(jobName, getPageCount(jobName, city));
            }

            for (Map.Entry<String, Integer> entry : pageNum.entrySet()) {
                parseJobs(entry.getKey(), city, entry.getValue());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void parseJobs(String job, String city, int totalPage) throws Exception {
        Map<String, Map<String, Map<String, Object>>> maps = null;
        List<JobConfig> list = new ArrayList<JobConfig>();
        for (int i = 1; i <= totalPage; i++) {
            String url = "http://www.lagou.com/jobs/positionAjax.json?city=" + city + "&kd=" + job + "&pn=" + i;
            Document doc = Jsoup.connect(url).ignoreContentType(true).timeout(10000).get();
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
                JobConfig jobConfig = new JobConfig(job, companyCity, companyName, workYear, salary, education, financeStage, industryField, companySize);
                list.add(jobConfig);
            }
            write(list, "D:\\Project\\person\\bigdata\\jobs.txt");
            System.out.println("读取完第" + i + "页 一共有 " + totalPage + "页");
        }
    }

    public static Integer getPageCount(String job, String city) throws Exception {
        String url = "http://www.lagou.com/jobs/positionAjax.json?city=" + city + "&kd=" + job + "&pn=0";
        Document doc = Jsoup.connect(url).ignoreContentType(true).get();
        Element body = doc.body();
        Map<String, Map<String, Object>> maps = objectMapper.readValue(body.text(), Map.class);
        return (Integer) maps.get("content").get("totalPageCount");
    }

    public static void write(List<JobConfig> list, String url) throws Exception {
        File file = new File(url);
        BufferedWriter writer = new BufferedWriter(new FileWriter(file, true));
        for (JobConfig jobConfig : list) {
            writer.write(jobConfig.toString());
        }
        return;
    }
}
