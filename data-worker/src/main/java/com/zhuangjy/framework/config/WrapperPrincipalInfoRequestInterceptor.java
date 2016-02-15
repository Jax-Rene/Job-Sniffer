package com.zhuangjy.framework.config;

import com.zhuangjy.framework.utils.Util;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.util.StringUtils;

import java.io.IOException;

/**
 * Author:王旗
 * Date:2015/7/2 15:45
 * Description:
 */
public class WrapperPrincipalInfoRequestInterceptor implements ClientHttpRequestInterceptor {

    private Logger logger = LoggerFactory.getLogger(WrapperPrincipalInfoRequestInterceptor.class);
    
    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
        if(logger.isDebugEnabled()) {
            if (body.length > 0) {
                String tmp = IOUtils.toString(body, "UTF-8");
                if (StringUtils.hasText(tmp)) {
                    logger.debug("request uri : {} , param : {} ", request.getURI().toString(), tmp.substring(0, Math.min(tmp.length(), 1024)));
                } else {
                    logger.debug("request uri : {}", request.getURI().toString());
                }
            } else {
                logger.debug("request uri : {}", request.getURI().toString());
            }
        }
        try {
            if (Util.getRequest() != null) {
                request.getHeaders().add("principal", Util.getPrincipal());
                request.getHeaders().add("admin", Util.isAdmin() + "");
                request.getHeaders().add("loginIp",Util.getLoginIp());
            }
        } catch (Exception e) {
            logger.error("", e);
        }
        return execution.execute(request, body);
    }
    
}
