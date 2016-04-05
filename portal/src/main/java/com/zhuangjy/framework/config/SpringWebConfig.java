package com.zhuangjy.framework.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.MessageCodesResolver;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.resource.DefaultServletHttpRequestHandler;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

import java.util.Locale;

/**
 * Author:Johnny
 * Date:2014/9/18 16:02
 * Description:
 */
@Configuration
public class SpringWebConfig extends WebMvcConfigurerAdapter {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        super.addResourceHandlers(registry);
        registry.addResourceHandler("/img/**", "/css/**", "/js/**", "/fonts/**").addResourceLocations("/img/", "/css/","/js/","/fonts/").setCachePeriod(600);
    }

    @Override
    public MessageCodesResolver getMessageCodesResolver() {
        return new MyMessageCodesResolver();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        super.addInterceptors(registry);
        LocaleChangeInterceptor localeChangeInterceptor = new LocaleChangeInterceptor();
        registry.addInterceptor(localeChangeInterceptor);
    }

    @Bean
    public LocaleResolver localeResolver() {
        CookieLocaleResolver cookieLocaleResolver = new CookieLocaleResolver();
        cookieLocaleResolver.setDefaultLocale(Locale.SIMPLIFIED_CHINESE);
        return cookieLocaleResolver;
    }

    @Bean(name = "jspViewResolver")
    public InternalResourceViewResolver jspViewResolver() {
        InternalResourceViewResolver jspView = new InternalResourceViewResolver();
        jspView.setSuffix(".jsp");
        jspView.setPrefix("/WEB-INF/jsp/");
        jspView.setViewClass(JstlView.class);
        jspView.setExposeContextBeansAsAttributes(true);
        return jspView;
    }



    @Bean
    public DefaultServletHttpRequestHandler createDefaultServletHttpRequestHandler() {
        return new DefaultServletHttpRequestHandler();
    }
}
