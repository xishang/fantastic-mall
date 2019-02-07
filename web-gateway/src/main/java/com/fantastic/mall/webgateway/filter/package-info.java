/**
 * @author xishang
 * @version 1.0
 * @since 2018/6/19
 *
 * === 核心过滤器:
 * -> pre过滤器:
 * -3: ServletDetectionFilter: 检测当前请求是通过`DispatcherServlet`还是`ZuulServlet`(/zuul/*)处理
 * -2: Servlet30WrapperFilter
 * -1: FormBodyWrapperFilter
 * 1: DebugFilter
 * 5: PreDecorationFilter
 * -> route过滤器:
 * 10: RibbonRoutingFilter
 * 100: SimpleHostRoutingFilter
 * 500: SendForwardFilter
 * -> post过滤器
 * 0: SendErrorFilter
 * 1000: SendResponseFilter
 */
package com.fantastic.mall.webgateway.filter;