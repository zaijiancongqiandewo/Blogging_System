package com.lsr.blogging.filters;

import com.lsr.blogging.common.Result;
import com.lsr.blogging.util.WebUtil;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
//用于跨域资源共享（CORS）的机制，在某些情况下浏览器在发送实际的跨域请求之前发起的一种 OPTIONS 请求。
@WebFilter("/*")
public class CrosFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        HttpServletRequest request =(HttpServletRequest) servletRequest;
        response.setHeader("Access-Control-Allow-Origin", "*");//允许跨域请求的源地址
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE, HEAD");//允许跨域请求的方法。
        response.setHeader("Access-Control-Max-Age", "3600");//允许跨域请求的特定头部字段。表示客户端的预检请求结果可以缓存多久，以避免在多次跨域请求中反复校验。单位是秒，因此设置为 3600 即表示缓存 1 小时。
        response.setHeader("Access-Control-Allow-Headers", "access-control-allow-origin, authority, content-type, version-info, X-Requested-With");
        // 非预检请求,放行即可,预检请求,则到此结束,不需要放行
        if(!request.getMethod().equalsIgnoreCase("OPTIONS")){
            filterChain.doFilter(servletRequest, servletResponse);
        }
        else{
            WebUtil.writeJson(response, Result.ok(null));
        }
    }
}
