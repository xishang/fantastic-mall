package com.fantastic.mall.webgateway.filter;

import com.netflix.zuul.FilterProcessor;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;

import java.util.logging.Filter;

/**
 * @author xishang
 * @version 1.0
 * @since 2018/6/20
 *
 * 需要手动设置: FilterProcessor.setProcessor(new CustomFilterProcessor());
 */
public class CustomFilterProcessor extends FilterProcessor {

    @Override
    public Object processZuulFilter(ZuulFilter filter) throws ZuulException {
        try {
            return super.processZuulFilter(filter);
        } catch (ZuulException e) {
            RequestContext context = RequestContext.getCurrentContext();
            // 存储当前filter类型: pre, route or post, 方便自定义处理
            context.set("failed-filter", filter);
            throw e;
        }
    }

}
