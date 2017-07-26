package com.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;

public class CommonInterceptor implements HandlerInterceptor {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    //	@Resource
    //	private CheckService checkService;

    /**
     * 在业务处理器处理请求之前被调用 如果返回false 从当前的拦截器往回执行所有拦截器的afterCompletion(),再退出拦截器链
     * <p/>
     * 如果返回true 执行下一个拦截器,直到所有的拦截器都执行完毕 再执行被拦截的Controller 然后进入拦截器链,
     * 从最后一个拦截器往回执行所有的postHandle() 接着再从最后一个拦截器往回执行所有的afterCompletion()
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        // 返回响应报文中的头部信息
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "OPTIONS, GET, POST, PUT, DELETE");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers", "X-Requested-With, Content-Type, Accept, X-Token");

        // 1、记录日志
        StringBuilder sb = new StringBuilder();
        Enumeration<?> a = request.getParameterNames();
        while (a.hasMoreElements()) {
            Object name = (Object) a.nextElement();
            Object value = request.getParameter(String.valueOf(name));
            sb.append(name).append("=").append(value).append("&");
        }
        if (sb.length() > 0) {
            sb.delete(sb.length() - 1, sb.length());
        }

        String reqMethod = request.getMethod(); // 请求方法
        String reqUrl = request.getRequestURI(); // 请求路径

        StringBuilder sbLog = new StringBuilder();
        sbLog
                .append(reqMethod)
                .append("->")
                .append(reqUrl)
                .append("[")
                .append(sb.toString())
                .append("]");
        LOGGER.info(sbLog.toString());

        // 统一处理跨域请求方法options请求
        if ("options".equals(reqMethod.toLowerCase())) {
            writerJson(response, new Res<String>(ResultCode.CODE_0000, ResultCode.MSG_0000, null));
            return false;
        }

        return true;
    }

    /**
     * 在业务处理器处理请求执行完成后,生成视图之前执行的动作
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {
    }

    /**
     * 在DispatcherServlet完全处理完请求后被调用
     * <p/>
     * 当有拦截器抛出异常时,会从当前拦截器往回执行所有的拦截器的afterCompletion()
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
    }

    private void writerJson(HttpServletResponse response, Res<String> res) throws IOException {
        PrintWriter out = response.getWriter();
        try {
            response.setStatus(HttpStatus.OK.value());
            response.setHeader("Content-type", "application/json;charset=UTF-8");
            response.setCharacterEncoding("UTF-8");
            out.print(new Gson().toJson(res));
            out.flush();
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        } finally {
            if (out != null) {
                out.close();
            }
        }
    }}
