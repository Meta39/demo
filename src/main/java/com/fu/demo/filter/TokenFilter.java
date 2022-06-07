package com.fu.demo.filter;

import com.fu.demo.config.NotCheckConfig;
import com.fu.demo.entity.Code;
import com.fu.demo.entity.Err;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;

import javax.annotation.Resource;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * Filter拦截器，token拦截
 */
@Component
@WebFilter(urlPatterns = "/*", filterName = "tokenFilter")
public class TokenFilter implements Filter {
    @Value("${token-overtime}")
    private int tokenOvertime;
//    @Value("${not-check}")
//    private String notCheck;
    //yml list形式过滤
    @Resource
    private NotCheckConfig notCheckConfig;
    @Resource
    private RedisTemplate<String, Object> redisTemplate;
    @Resource
    @Qualifier("handlerExceptionResolver")
    private HandlerExceptionResolver resolver;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        String token = request.getHeader("token");//请求头的token
        String requestURI = request.getRequestURI();//请求地址
//        System.out.println(requestURI);
        boolean pass = false;
//        for (String uri : notCheck.split(",")) {//@Value配置
        for (String uri : notCheckConfig.getNotCheck()) {//yml list配置
            //请求地址是否是白名单
            if (requestURI.contains(uri)) {
                pass = true;
                break;
            }
        }
        if (pass) {
            filterChain.doFilter(request, response);//通过认证
        } else if (StringUtils.isNotBlank(token) && redisTemplate.hasKey(token)) {//token认证
            redisTemplate.expire(token,tokenOvertime,TimeUnit.SECONDS);//刷新token过期时间
            filterChain.doFilter(request, response);//通过认证
        } else {//非法请求拦截，抛出异常
            //抛出异常
            resolver.resolveException(request, response, null, StringUtils.isBlank(token) ? new Err("请求头token不能为空！") : new Err(Code.NOT_LOGIN.getNum(),Code.NOT_LOGIN.getMsg()));
        }
    }
}

