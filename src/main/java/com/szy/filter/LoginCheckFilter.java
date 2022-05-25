package com.szy.filter;

import com.alibaba.fastjson.JSON;
import com.szy.common.BaseContext;
import com.szy.common.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.AntPathMatcher;

import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
@Slf4j
@WebFilter(filterName = "LoginCheckFilter", urlPatterns = "/*")
public class LoginCheckFilter implements Filter {
    //路径匹配器
    public static final AntPathMatcher PATH_MATCHER = new AntPathMatcher();

    public void init(FilterConfig config) throws ServletException {
    }

    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws ServletException, IOException {
        servletRequest.setCharacterEncoding("utf-8");
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        String requestURI = request.getRequestURI();
        //不需要过滤的uri
        String[] uris = {
                "/employee/login",
                "/employee/logout",
                "/backend/**",
                "/front/**",
                "/user/sendMsg",
                "/user/login"
        };
        //如果请求uri不需要过滤，则直接放行
        if(check(uris, requestURI)){
            chain.doFilter(request,response);
            return;
        }
        //如果已经登录，则直接放行
        if(request.getSession().getAttribute("employee") != null){
            Long id = (Long) request.getSession().getAttribute("employee");
            BaseContext.setCurrentId(id);
            chain.doFilter(request,response);
            return;
        }

        if(request.getSession().getAttribute("user") != null){
            Long id = (Long) request.getSession().getAttribute("user");
            BaseContext.setCurrentId(id);
            chain.doFilter(request,response);
            return;
        }

        response.getWriter().write(JSON.toJSONString(R.error("NOTLOGIN")));
        return;
    }

    public boolean check(String[] uris, String requestURI){
        for(String uri : uris){
            if(PATH_MATCHER.match(uri, requestURI)){
                return true;
            }
        }
        return false;
    }
}
