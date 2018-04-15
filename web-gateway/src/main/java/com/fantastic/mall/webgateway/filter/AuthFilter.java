package com.fantastic.mall.webgateway.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * @author xishang
 * @version 1.0
 * @since 2018/4/10
 */
@Component
public class AuthFilter extends ZuulFilter {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * filterType: 过滤器的类型
     * -> pre: 路由之前
     * -> routing: 路由之时
     * -> post: 路由之后
     * -> error: 错误之后
     *
     * @return
     */
    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() {
        RequestContext context = RequestContext.getCurrentContext();
        HttpServletRequest request = context.getRequest();
        logger.info("auth filter: method = {}, url = {}", request.getMethod(), request.getRequestURL());
        Object accessToken = request.getParameter("token");
        if (accessToken == null) {
            logger.warn("token is empty");
            context.setSendZuulResponse(false);
            context.setResponseStatusCode(401);
            try {
                context.getResponse().getWriter().write("token is empty");
            } catch (Exception e) {
            }

            return null;
        }
        return null;
    }

}
