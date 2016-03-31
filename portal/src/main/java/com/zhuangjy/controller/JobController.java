package com.zhuangjy.controller;

import com.zhuangjy.service.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by johnny on 16/3/31.
 */
@RestController
@RequestMapping(value = "/job")
public class JobController {
    @Autowired
    private JobService jobService;
}
