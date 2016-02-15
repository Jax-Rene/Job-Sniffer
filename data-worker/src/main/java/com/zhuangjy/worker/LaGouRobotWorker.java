package com.zhuangjy.worker;

import com.zhuangjy.dao.BaseDao;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import java.io.IOException;
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
        return orginUrl.replace("kd=","kd=" + jobName);
    }

    @Override
    public  Integer getPageCount() throws IOException {
        Document doc = Jsoup.connect(url + 0).ignoreContentType(true).timeout(1000000).get();
        Element body = doc.body();
        Map<String, Map<String, Object>> maps = objectMapper.readValue(body.text(), Map.class);
        return (Integer) maps.get("content").get("totalPageCount");
    }

    @Override
    public void run() {
        super.run();
    }
}
