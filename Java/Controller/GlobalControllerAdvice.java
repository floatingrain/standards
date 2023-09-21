package com.harvestmoney.microdistribution.controller.advice;

import java.io.IOException;
import java.lang.reflect.Type;

import javax.servlet.http.HttpServletResponse;

import org.springframework.core.MethodParameter;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import com.harvestmoney.microdistribution.controller.advice.entity.Response;
import com.harvestmoney.microdistribution.controller.advice.entity.ResponseUtil;
import com.harvestmoney.microdistribution.exception.BusinessException;

import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice(basePackages = "com.harvestmoney.microdistribution.controller")
@Slf4j
public class GlobalControllerAdvice implements RequestBodyAdvice, ResponseBodyAdvice<Object> {

    /**
     * 自定义业务异常
     */
    @ExceptionHandler(BusinessException.class)
    public Response<String> handleBusinessException(BusinessException e, HttpServletResponse response) {
        log.error("业务异常");
        return ResponseUtil.error(response.getStatus(), e.getCode(), e.getMessage(), e.getMsg());
    }

    /**
     * 其他异常
     */
    @ExceptionHandler(Exception.class)
    public Response<String> handleException(Exception e, HttpServletResponse response) {
        log.error("服务器异常", e);
        return ResponseUtil.error(response.getStatus(), null, e.getMessage(), "服务器异常");
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType,
            Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request,
            ServerHttpResponse response) {
        // 获取请求的相对路径
        String uri = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest()
                .getRequestURI();
        // 适配错误响应，因为已经返回的是响应格式
        if (body instanceof Response) {
            log.info("请求接口：{}\n响应报文：\n{}", uri, JSONUtil.toJsonPrettyStr(body));
            return body;
            // 适配String响应，因为HttpMessageConverter会认为可以使用StringHttpMessageConverter，再用ResponseUtil封装之后不再是String，写数据时就会报错。
        } else if (body instanceof String) {
            log.info("请求接口：{}\n响应报文：\n{}", uri, JSONUtil.toJsonPrettyStr(ResponseUtil.success(body)));
            return JSONUtil.toJsonPrettyStr(ResponseUtil.success(body));
        } else {
            // 其他情况，统一返回成功结果
            log.info("请求接口：{}\n响应报文：\n{}", uri, JSONUtil.toJsonPrettyStr(ResponseUtil.success(body)));
            return ResponseUtil.success(body);
        }
    }

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return true;
    }

    @Override
    public boolean supports(MethodParameter methodParameter, Type targetType,
            Class<? extends HttpMessageConverter<?>> converterType) {
        return true;
    }

    @Override
    public HttpInputMessage beforeBodyRead(HttpInputMessage inputMessage, MethodParameter parameter, Type targetType,
            Class<? extends HttpMessageConverter<?>> converterType) throws IOException {
        return inputMessage;
    }

    @Override
    public Object afterBodyRead(Object body, HttpInputMessage inputMessage, MethodParameter parameter, Type targetType,
            Class<? extends HttpMessageConverter<?>> converterType) {
        log.info("请求接口：{}\n请求参数：\n{}",
                ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getRequestURI(),
                JSONUtil.toJsonPrettyStr(body));
        return body;
    }

    @Override
    public Object handleEmptyBody(Object body, HttpInputMessage inputMessage, MethodParameter parameter,
            Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
        return body;
    }
}
