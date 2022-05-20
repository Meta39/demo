package com.fu.demo;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fu.demo.aop.IgnoreResAnnotate;
import com.fu.demo.entity.Err;
import com.fu.demo.entity.Res;
import org.springframework.core.MethodParameter;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.lang.reflect.AnnotatedElement;
import java.util.LinkedHashMap;

@RestControllerAdvice
public class ReturnRes implements ResponseBodyAdvice<Object> {

    @Override
    public boolean supports(MethodParameter methodParameter, Class<? extends HttpMessageConverter<?>> aClass) {
        //判断是否有加自定义注解，有就跳过，不返回Res
        AnnotatedElement annotatedElement = methodParameter.getAnnotatedElement();
        IgnoreResAnnotate ignoreResAnnotate = AnnotationUtils.findAnnotation(annotatedElement, IgnoreResAnnotate.class);
        return ignoreResAnnotate == null;
    }

    @Override
    public Object beforeBodyWrite(Object o, MethodParameter methodParameter, MediaType mediaType, Class<? extends HttpMessageConverter<?>> aClass, ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {
        if (o instanceof LinkedHashMap) {//粗暴解决404问题
            LinkedHashMap map = (LinkedHashMap) o;//强转
            if (map.get("status") != null) {
                int status = (int) map.get("status");
//                if (404 == status) return Res.err("404找不到页面");
                if (404 == status) throw new Err(404,"404找不到页面");
            }
        } else if (o instanceof String) {//String要特殊处理
            try {
                return new ObjectMapper().writeValueAsString(new Res(o));
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        } else if (o instanceof Res) {//本身是Res直接返回即可
            return o;
        }
        return new Res(o);
    }
}
