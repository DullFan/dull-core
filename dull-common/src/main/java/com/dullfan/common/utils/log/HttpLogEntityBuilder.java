package com.dullfan.common.utils.log;

import com.alibaba.fastjson2.JSON;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.StopWatch;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static com.dullfan.common.utils.ip.IpUtils.getIpAddr;

/**
 * HttpLogEntity构造器
 */
public class HttpLogEntityBuilder {

    /**
     * 构建HTTP日志对象
     */
    public static HttpLogEntity build(ContentCachingRequestWrapper requestWrapper, ContentCachingResponseWrapper responseWrapper, StopWatch stopWatch) {
        HttpLogEntity httpLogEntity = new HttpLogEntity();
        httpLogEntity.setRequestUri(requestWrapper.getRequestURI())
                .setMethod(requestWrapper.getMethod())
                .setRemoteAddr(requestWrapper.getRemoteAddr())
                .setIp(getIpAddr(requestWrapper))
                .setRequestHeaders(getRequestHeaderMap(requestWrapper));
        if (requestWrapper.getMethod().equals(RequestMethod.GET.name())) {
            httpLogEntity.setRequestParams(JSON.toJSONString(requestWrapper.getParameterMap()));
        } else {
            httpLogEntity.setRequestParams(new String(requestWrapper.getContentAsByteArray()));
        }
        String responseContentType = responseWrapper.getContentType();
        if (StringUtils.equals("application/json;charset=UTF-8", responseContentType)) {
            httpLogEntity.setResponseData(new String(responseWrapper.getContentAsByteArray()));
        } else {
            httpLogEntity.setResponseData("Stream Body...");
        }
        httpLogEntity.setStatus(responseWrapper.getStatus())
                .setResponseHeaders(getResponseHeaderMap(responseWrapper))
                .setResolveTime(stopWatch.toString());
        return httpLogEntity;
    }

    /**
     * 获取请求头MAP
     */
    public static Map<String, String> getRequestHeaderMap(HttpServletRequest request) {
        Map<String, String> result = new HashMap<>();
        if (Objects.nonNull(request)) {
            Enumeration<String> headerNames = request.getHeaderNames();
            while (headerNames.hasMoreElements()) {
                String headerName = headerNames.nextElement();
                String headerValue = request.getHeader(headerName);
                result.put(headerName, headerValue);
            }
        }
        return result;
    }

    /**
     * 获取响应头MAP
     */
    public static Map<String, String> getResponseHeaderMap(HttpServletResponse response) {
        Map<String, String> result = new HashMap<>();
        if (Objects.nonNull(response)) {
            String contentType = response.getContentType();
            result.put("contentType", contentType);
        }
        return result;
    }

}
