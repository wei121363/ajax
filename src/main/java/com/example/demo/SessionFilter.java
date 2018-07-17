package com.example.demo;


import org.springframework.core.annotation.Order;
import org.springframework.util.StringUtils;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@WebFilter(urlPatterns ={ "/*"})
@Order(value = 2)
public class SessionFilter implements Filter{
    //允许跳过filter的路径
    private static final Set<String> ALLOWED_PATHS = Collections.unmodifiableSet(new HashSet<>(
            Arrays.asList("/login")));
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {


        HttpServletRequest httpRequest=(HttpServletRequest)request;
        HttpServletResponse httpResponse=(HttpServletResponse)response;

        String path = httpRequest.getRequestURI().substring(httpRequest.getContextPath().length()).replaceAll("[/]+$", "");
      if(path.indexOf(";")>-1)
      {  path=path.substring(0,path.indexOf(";"));}
        boolean allowedPath = ALLOWED_PATHS.contains(path);


        HttpSession session = httpRequest.getSession(false);

        String jsessionid=session==null?null:session.getId();

        if ((session!=null && null != session.getAttribute("login")) || allowedPath) {

            if(StringUtils.hasText(jsessionid))
            {
                //设置头部参数，传递给前端界面
                 httpResponse.setHeader("jsessionid",jsessionid);
            }
             chain.doFilter(request, response);

             return;
        }
//如果session为空，则超时错误
        if(session==null || session.getAttribute("login")==null)
       {
           httpResponse.setStatus(403);
       }



       return;

    }

    @Override
    public void destroy() {

    }
}
