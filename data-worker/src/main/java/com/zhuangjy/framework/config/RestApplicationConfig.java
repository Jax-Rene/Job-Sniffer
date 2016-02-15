package com.zhuangjy.framework.config;

import com.zhuangjy.framework.spring.SpringContextHolder;
import com.zhuangjy.framework.spring.SpringPropertyPlaceholderConfigurer;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.ResourceHttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.converter.support.AllEncompassingFormHttpMessageConverter;
import org.springframework.http.converter.xml.SourceHttpMessageConverter;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

import javax.xml.transform.Source;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


@Configuration
@EnableWebMvc
@EnableAutoConfiguration
@EnableScheduling
@ComponentScan(basePackages = {"com.zhuangjy"})
public class RestApplicationConfig {

    @Bean
    public RestTemplate restTemplate() {
        RestTemplate template = new RestTemplate(converters());
        template.setRequestFactory(httpClientFactory());
        template.setInterceptors(Collections.<ClientHttpRequestInterceptor>singletonList(new WrapperPrincipalInfoRequestInterceptor()));
        return template;
    }

    @Bean
    public RequestMappingHandlerAdapter requestMappingHandlerAdapter() {
        RequestMappingHandlerAdapter bean = new RequestMappingHandlerAdapter();
        bean.setMessageConverters(converters());
        return bean;
    }

    @Bean
    public static SpringPropertyPlaceholderConfigurer propertyPlaceholderConfigurer() {
        SpringPropertyPlaceholderConfigurer configurer = new SpringPropertyPlaceholderConfigurer();
        configurer.setFileEncoding("UTF-8");
        configurer.setIgnoreResourceNotFound(true);
        configurer.setSystemPropertiesMode(PropertyPlaceholderConfigurer.SYSTEM_PROPERTIES_MODE_OVERRIDE);
        configurer.setLocations(
                new Resource[]{
                        new ClassPathResource("database.properties"),
                        new ClassPathResource("job.properties")
                }
        );
        return configurer;
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
    public BasicCredentialsProvider credentialProvider() {
        BasicCredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        return credentialsProvider;
    }

    @Bean
    public org.apache.http.impl.client.CloseableHttpClient httpClient() {
        return HttpClients.custom().setDefaultCredentialsProvider(credentialProvider()).setConnectionManager(multiThreadedConnectionManager()).build();
    }

    @Bean
    public HttpClientConnectionManager multiThreadedConnectionManager() {
        PoolingHttpClientConnectionManager manager = new PoolingHttpClientConnectionManager();
        manager.setDefaultMaxPerRoute(3);
        manager.setMaxTotal(30);
        return manager;
    }

    @Bean
    public HttpComponentsClientHttpRequestFactory httpClientFactory() {
        HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
        factory.setConnectTimeout(connTimeout);
        factory.setReadTimeout(readTimeout);
        factory.setHttpClient(httpClient());
        return factory;
    }

    @Bean
    public AuthScope authScope() {
        AuthScope authScope = new AuthScope(null, -1, null, null);
        return authScope;
    }

    @Bean
    @Qualifier("springContextHolder")
    public SpringContextHolder contextHolder() {
        return new SpringContextHolder();
    }

    @Value("${rest.connTimeout}")
    private Integer connTimeout;
    @Value("${rest.readTimeout}")
    private Integer readTimeout;


    public static void main(String[] args) {
        SpringApplication.run(RestApplicationConfig.class, args);
    }
}
