package com.zhuangjy.controller;

import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by johnny on 16/5/11.
 */
@RestController
@RequestMapping("/data-report")
public class DataReportController {

    @RequestMapping(value = "/load", method = RequestMethod.POST)
    public Map<String, Object> load(Integer start, Integer limit, String startTime, String endTime, String phone) throws IOException {
//        if (StringUtils.isEmpty(startTime))
//            startTime = "2000-01-01F00:00:00";
//        else
//            startTime = DateUtil.parseDateTimeToLocal(startTime);
//        if (StringUtils.isEmpty(endTime))
//            endTime = LocalDateTime.now().plusYears(1000).toString();
//        else
//            endTime = DateUtil.parseDateTimeToLocal(endTime);
//        List<Order> list = orderService.load(start, limit, startTime, endTime, phone, 1);
//        for (Order order : list) {
//            order.setOrderTime(DateUtil.parseLocalDateTime(LocalDateTime.parse(order.getOrderTime())));
//        }
//        Map<String, Object> map = new HashMap<>();
//        map.put("records", list);
//        map.put("totalCount", orderService.countMsgNum(startTime, endTime, phone, 1));
//        LOGGER.info("load order data successfully!");
//        return map;
        return null;
    }
}
