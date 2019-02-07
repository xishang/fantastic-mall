package com.fantastic.mall.webgateway.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.springframework.cloud.netflix.zuul.filters.post.SendErrorFilter;

/**
 * @author xishang
 * @version 1.0
 * @since 2018/6/20
 *
 * 自定义ErrorFilter: 继承SendErrorFilter, 用于处理post过滤器的异常输出
 */
public class CustomErrorFilter extends SendErrorFilter {

    /**
     * 大于SendErrorFilter的order
     * @return
     */
    @Override
    public int filterOrder() {
        return 30;
    }

    @Override
    public boolean shouldFilter() {
        RequestContext context = RequestContext.getCurrentContext();
        ZuulFilter failedFilter = (ZuulFilter) context.get("failed-filter");
        // 只处理post过滤器异常
        if (failedFilter != null && "post".equals(failedFilter.filterType())) {
            return true;
        }
        return false;
    }

}
