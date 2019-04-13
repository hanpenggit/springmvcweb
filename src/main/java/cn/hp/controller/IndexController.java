package cn.hp.controller;

import cn.hp.annotations.OperateLog;
import cn.hp.service.TestService;
import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/")
public class IndexController {
    @Autowired
    TestService testService;
    @RequestMapping("/index")
    public String index() {
        return "index";
    }
    @RequestMapping(value = "/list",produces = "text/html;charset=UTF-8")
    @ResponseBody
    @OperateLog(value="自定义注解demo")
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
