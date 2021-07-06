package com.example.demo.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.util.HashMap;

/**
 * ClassName:MyResponseBodyAdvice
 * Package:com.example.demo.config
 * Description:
 *
 * @Author:HP
 * @date:2021/7/6 9:30
 */
public class MyResponseBodyAdvice implements ResponseBodyAdvice {

    @Autowired
    private ObjectMapper mapper;

    @Override
    public boolean supports(MethodParameter methodParameter, Class aClass) {
        return true;
    }

    @SneakyThrows
    @Override
    public Object beforeBodyWrite(Object o, MethodParameter methodParameter, MediaType mediaType, Class aClass, ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("status", 0);
        map.put("data", o);
        map.put("msg", "");
        if (o instanceof String) { // 后端接口
            // 当前方法要给前端返回一个 json 类型的字符串
            serverHttpResponse.getHeaders().setContentType(
                    MediaType.APPLICATION_JSON);
            // 返回一个 json 字符串
            return mapper.writeValueAsString(map);
        }
        return map;
    }
}
