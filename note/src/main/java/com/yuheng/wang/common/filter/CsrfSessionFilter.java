package com.yuheng.wang.common.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * CSRF跨站请求伪造防御，继承OncePerRequestFilter确保在一次请求只通过一次filter
 * 
 * @author wangyuheng
 * @date 2018/09/26
 */
public class CsrfSessionFilter extends OncePerRequestFilter {
    private static final Logger log = LoggerFactory.getLogger(CsrfSessionFilter.class);

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
        throws ServletException, IOException {
        // TODO Auto-generated method stub
        log.info(">>> before chain.doFilter()...");
        filterChain.doFilter(request, response);
        log.info(">>> after chain.doFilter()...");
        
        String alreadyFilteredAttributeName = super.getAlreadyFilteredAttributeName();
        Object attribute = request.getAttribute(alreadyFilteredAttributeName);
        boolean hasAlreadyFilteredAttribute = attribute != null;

    }

    @Override
    protected void initFilterBean() throws ServletException {
        log.info(">>> start CsrfSessionFilter init()...");
        // 获取FilterConfig
        FilterConfig filterConfig = super.getFilterConfig();
        if (filterConfig != null) {
            // 获取配置的参数
            String excludeUrl = filterConfig.getInitParameter("excludeUrl");
            log.info("excludeUrl:" + excludeUrl);

            String filterName = filterConfig.getFilterName();
            log.info("filterName:{}", filterName);
            
            

        }

        log.info(">>> close CsrfSessionFilter init()...");
    }

    @Override
    public void destroy() {
        log.info(">>> CsrfSessionFilter destroy()...");
    }

}
