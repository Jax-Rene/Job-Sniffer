package com.zhuangjy.framework.config;

import com.zhuangjy.entity.PropertiesMap;
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
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

@Configuration
@EnableAutoConfiguration
@EnableWebMvc
@EnableAsync
@ComponentScan(basePackages = {"com.zhuangjy"})
@PropertySource(value = {"file:/Users/johnny/Desktop/JobsAnalysis/analysis.properties"})
@ImportResource("classpath:spring-security.xml")
public class AppsApplicationConfig {
    @Value("${username}")
    private String userName;
    @Value("${password}")
    private String passWord;
    @Value("${email_name}")
    private String emailName;
    @Value("${email_pass}")
    private String emailPass;
    @Value("${email_host}")
    private String emailHost;

    @Bean
    public JavaMailSenderImpl mailSender(){
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setDefaultEncoding("UTF-8");
        mailSender.setHost(emailHost);
        mailSender.setUsername(emailName);
        mailSender.setPassword(emailPass);
        Properties prop = new Properties();
        prop.put("mail.smtp.auth","true");
        prop.put("mail.smtp.timeout","25000");
        mailSender.setJavaMailProperties(prop);
        return mailSender;
    }


    @Bean
    public PropertiesMap propertiesMap(){
        PropertiesMap p = new PropertiesMap();
        p.setUserName(userName);
        p.setPassWord(passWord);
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
        converters.add(new SourceHttpMessageConverter<>());
        converters.add(new AllEncompassingFormHttpMessageConverter());
        MappingJackson2HttpMessageConverter jconfig = new MappingJackson2HttpMessageConverter();
        jconfig.setPrettyPrint(true);
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
