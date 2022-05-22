package com.fu.demo;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fu.demo.aop.IgnoreResAnnotate;
import com.fu.demo.entity.Err;
import com.fu.demo.entity.Res;
import org.apache.commons.lang.StringUtils;
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
         if (o instanceof String) {//String要特殊处理
            try {
                return new ObjectMapper().writeValueAsString(new Res(o));
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        } else if (o instanceof Res) {//本身是Res直接返回即可
            return o;
        }else if (o instanceof LinkedHashMap) {//解决404、500等spring没有捕获的异常问题，只能放到最后的判断条件去判断
             LinkedHashMap map = (LinkedHashMap) o;//强转
             if (map.get("status") != null) {
                 int status = (int) map.get("status");
                 String error = (String) map.get("error");
                 String message = (String) map.get("message");
                 String path = (String) map.get("path");
                 throw new Err(status, "请求："+path+"，发送错误："+(StringUtils.isNotBlank(error)?error:message));
             }
         }
        return new Res(o);
    }
}
