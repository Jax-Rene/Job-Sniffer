package com.zhuangjy.framework.config;

import com.zhuangjy.framework.spring.SpringContextHolder;
import com.zhuangjy.framework.spring.SpringContextUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.boot.context.properties.ConfigurationProperties;
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
import java.util.List;

/**
 * Author:王旗
 * Date:2014/9/12 11:37
 * Description:
 */
@Configuration
@EnableWebMvc
@EnableAsync
@EnableScheduling
@PropertySource(ignoreResourceNotFound = true, value = {"classpath:service.properties", "file:d:\\etc\\eagle\\eagle.properties", "file:d:\\etc\\eagle\\service.properties", "file:d:\\etc\\eagle\\eagle-apps\\service.properties"})
@ComponentScan(basePackages = {"com.zhuangjy"})
@ImportResource(value = {"classpath*:/applicationContext.xml", "classpath*:/spring.xml", "classpath*:/spring-*.xml"}, reader = XmlBeanDefinitionReader.class)
public class AppsApplicationConfig {

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
