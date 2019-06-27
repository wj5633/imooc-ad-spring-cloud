package com.wj5633.ad.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * Created with IntelliJ IDEA.
 *
 * @author wj
 * @version 1.0.0
 * @create 2019/1/29 21:43
 * @description
 */
@Slf4j
@Component
public class AccessLogFilter extends ZuulFilter {
    @Override
    public String filterType() {
        return FilterConstants.POST_TYPE;
    }

    @Override
    public int filterOrder() {
        return FilterConstants.SEND_RESPONSE_FILTER_ORDER - 1;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() throws ZuulException {
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();

        long startTime = (long) ctx.get("startTime");
        long duration = System.currentTimeMillis() - startTime;

        String message = "";
        Throwable throwable = ctx.getThrowable();
        if (throwable != null) {
            message = throwable.getMessage();
        }

        log.info(String.format("uri: %s, method: %s, code: %s error: %s, duration: %sms", request.getRequestURI(),
                request.getMethod(), ctx.getResponseStatusCode(), message, duration / 100));
        return null;
    }
}
