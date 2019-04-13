package cn.hp.inteceptor;

import cn.hp.annotations.OperateLog;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class OperateLogInteceptor extends HandlerInterceptorAdapter {
    private static Logger logger = LoggerFactory.getLogger(OperateLogInteceptor.class);
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HandlerMethod methodHandler=(HandlerMethod) handler;
        OperateLog auth=methodHandler.getMethodAnnotation(OperateLog.class);
        if (auth!=null) {
            logger.info(auth.write()+" "+auth.value());
        }else{
        }
        logger.info(auth+"----------------------------");
        return true;
    }

}
