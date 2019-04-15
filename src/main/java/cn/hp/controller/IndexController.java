package cn.hp.controller;

import cn.hp.annotations.OperateLog;
import cn.hp.realm.CustomRealm;
import cn.hp.service.TestService;
import com.alibaba.fastjson.JSON;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/")
public class IndexController {
    private static final Logger logger = LoggerFactory.getLogger(IndexController.class);
    @Autowired
    TestService testService;

    /**
     * 主页
     * @return
     */
    @RequestMapping("/index")
    public String index() {
        return "index";
    }

    /**
     * 跳转登录页面
     * @return
     */
    @RequestMapping("/login")
    public String login() {
        return "login";
    }

    /**
     * 登出
     * @return
     */
    @ResponseBody
    @RequestMapping("/logout")
    public String logout() {
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return "1";
    }

    /**
     * 登录
     * @param username
     * @param password
     * @return
     */
    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public String login_post(String username,String password,HttpServletRequest request) {
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(username,password);
        try {
            subject.login(usernamePasswordToken);
            logger.info("======登陆成功=======");
        }catch (AuthenticationException e){
            e.printStackTrace();
            logger.info("======登陆失败=======");
            request.setAttribute("msg","账号或者密码输入错误");
            return "login";
        }
        return "index";
    }

    /**
     * 进入未授权页面
     * @return
     */
    @RequestMapping("/unauthorized")
    public String unauthorized() {
        return "unauthorized";
    }
    @RequestMapping(value = "/list",produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String list(HttpServletRequest req) {
        Object obj=testService.list();
        return JSON.toJSONString(obj);
    }
    @RequestMapping("/insert")
    public String insert(HttpServletRequest req) {
        String  name=req.getParameter("name");
        Map<String,Object> params=new HashMap<>();
        params.put("name",name);
        Integer result=testService.insert(params);
        return "redirect:/list";
    }
    @RequestMapping("/update")
    public String update(HttpServletRequest req) {
        String  id=req.getParameter("id");
        String  name=req.getParameter("name");
        Map<String,Object> params=new HashMap<>();
        params.put("id",id);
        params.put("name",name);
        Integer result=testService.update(params);
        return "redirect:/list";
    }
    @RequestMapping("/delete")
    public String delete(HttpServletRequest req) {
        String  id=req.getParameter("id");
        Map<String,Object> params=new HashMap<>();
        params.put("id",id);
        Integer result=testService.delete(id);
        return "redirect:/list";
    }
}
