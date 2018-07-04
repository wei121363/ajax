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
    private static final Set<String> ALLOWED_PATHS = Collections.unmodifiableSet(new HashSet<>(
            Arrays.asList("/hello")));
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
         if ((session!=null && null != session.getAttribute("login")) || !allowedPath) {
            chain.doFilter(request, response);
            return;
        }
        /**
         * 当HTTP请求来自AJAX并且用户的Session已经超时时, 这时页面会没有任何反应, 因为向AJAX请求
         * 重定向或者输出一段JS实现跳转都是无法完成的. 因此这里实现当上述情况发生时, 向AJAX请求返
         * 回特定的标识(添加到响应对象中), 可以在定义AJAX请求完成回调方法时得到这个标识, 进而提示
         * 用户并完成JS跳转.
         * 配合东软前端超时或无会话则报403，前端收到403后提示会话超时直接流转登陆
         */

        String requestType = httpRequest.getHeader("abcd");


//         while(httpRequest.getHeaderNames().hasMoreElements()){
//            String headerName=(String)httpRequest.getHeaderNames().nextElement();
//            String headerValue=httpRequest.getHeader(headerName);
//            System.out.println(headerValue);
//        }


        System.out.println(httpRequest.getHeaderNames());
       if (!StringUtils.isEmpty(requestType) && requestType.equalsIgnoreCase("true")) {

             httpResponse.setStatus(403);
             //httpResponse.sendError(403);
         }
         else
        if(session==null || session.getAttribute("login")==null)
       {
           httpResponse.setStatus(403);
         //  httpResponse.sendError(403);
          // httpResponse.setHeader("status","403");

       }else
       {
           chain.doFilter(request, response);

       }

        /**
         * 由于Web端使用iframe嵌套, 因此直接重定向到登录页面并不能总是完成地很完美, 比如HTTP请求来自
         * iframe对象的时候, 只能让iframe加载到index.html, 体验不够好; 所以在这里将直接重定向改为向
         * 页面输出一段JS代码来实现使顶部window跳转到默认的登录页面.
         */
//        String jsCode = "<script type='text/javascript'>" +
//                "var p=window;while(p!=p.parent){p=p.parent; } p.location.href='" +
//                httpRequest.getContextPath() +
//                "/index.html'</script>";
        PrintWriter writer = httpResponse.getWriter();
         writer.print("{error:msg}");
        writer.flush();
        writer.close();
       return;

    }

    @Override
    public void destroy() {

    }
}
