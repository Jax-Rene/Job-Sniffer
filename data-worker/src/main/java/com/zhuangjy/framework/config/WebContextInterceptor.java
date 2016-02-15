package com.zhuangjy.framework.config;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Author:王旗
 * Date:2014/11/5 17:46
 * Description:
 */
public class WebContextInterceptor extends HandlerInterceptorAdapter {

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//        if(handler instanceof HandlerMethod) {
//            if (((HandlerMethod) handler).getBean() instanceof BaseController) {
//                ((BaseController) ((HandlerMethod) handler).getBean()).setRequest(request);
//                ((BaseController) ((HandlerMethod) handler).getBean()).setResponse(response);
//                ((BaseController) ((HandlerMethod) handler).getBean()).setPrincipal(request.getHeader("principal"));
//                ((BaseController) ((HandlerMethod) handler).getBean()).setLoginIp(request.getHeader("loginIp"));
//                ((BaseController) ((HandlerMethod) handler).getBean()).setAdmin(request.getHeader("admin") != null && request.getHeader("admin").equalsIgnoreCase("true"));
//            }
//        }

        return super.preHandle(request, response, handler);
    }
}
