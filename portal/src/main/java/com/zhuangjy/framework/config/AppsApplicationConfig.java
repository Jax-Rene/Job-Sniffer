package com.zhuangjy.framework.config;

import com.zhuangjy.framework.spring.SpringContextHolder;
import com.zhuangjy.framework.spring.SpringContextUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.*;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.ResourceHttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.converter.support.AllEncompassingFormHttpMessageConverter;
import org.springframework.http.converter.xml.SourceHttpMessageConverter;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

import javax.xml.transform.Source;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
@EnableAutoConfiguration
@EnableWebMvc
@EnableAsync
@ComponentScan(basePackages = {"com.zhuangjy"})
@PropertySource(ignoreResourceNotFound = true, value = {"classpath:analysis.properties"})
public class AppsApplicationConfig {
    @Value("${area}")
    private String area;
    @Value("${company_type}")
    private String companyType;
    @Value("${finance_stage}")
    private String financeStage;
    @Value("${education}")
    private String education;
    @Value("${username}")
    private String userName;
    @Value("${password}")
    private String passWord;

    @Bean
    public PropertiesMap propertiesMap(){
        PropertiesMap p = new PropertiesMap(area,companyType,financeStage,education,userName,passWord);
        return p;
    }

    @Bean
    public RequestMappingHandlerAdapter requestMappingHandlerAdapter() {
        RequestMappingHandlerAdapter bean = new RequestMappingHandlerAdapter();
        bean.setMessageConverters(converters());
        return bean;
    }

    public List<HttpMessageConverter<?>> converters() {
        List<HttpMessageConverter<?>> converters = new ArrayList<HttpMessageConverter<?>>();
        converters.add(new ByteArrayHttpMessageConverter());
        converters.add(new StringHttpMessageConverter(Charset.forName("UTF-8"))); //default 为ISO, 中文会乱码
        converters.add(new ResourceHttpMessageConverter());
        converters.add(new SourceHttpMessageConverter<Source>());
        converters.add(new AllEncompassingFormHttpMessageConverter());
        MappingJackson2HttpMessageConverter jconfig = new MappingJackson2HttpMessageConverter();
        jconfig.setPrettyPrint(true);
//        jconfig.getObjectMapper().setSerializationInclusion(JsonInclude.Include.NON_EMPTY);//过滤empty str/collections
        converters.add(jconfig);
        return converters;
    }

    @Bean
    @Qualifier("springContextUtils")
    public SpringContextUtils contextUtils() {
        SpringContextUtils bean = new SpringContextUtils();
        return bean;
    }

    @Bean
    @Qualifier("springContextHolder")
    public SpringContextHolder contextHolder() {
        return new SpringContextHolder();
    }
}
