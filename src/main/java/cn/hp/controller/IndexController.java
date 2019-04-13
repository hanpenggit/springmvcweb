package cn.hp.controller;

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
    @RequestMapping("/list")
    @ResponseBody
    public String list(HttpServletRequest req) {
        return JSON.toJSONString(testService.list());
    }
    @RequestMapping("/insert")
    @ResponseBody
    public String insert(HttpServletRequest req) {
        String  name=req.getParameter("name");
        Map<String,Object> params=new HashMap<>();
        params.put("name",name);
        Integer result=testService.insert(params);
        return result.toString();
    }
    @RequestMapping("/update")
    @ResponseBody
    public String update(HttpServletRequest req) {
        String  id=req.getParameter("id");
        String  name=req.getParameter("name");
        Map<String,Object> params=new HashMap<>();
        params.put("id",id);
        params.put("name",name);
        Integer result=testService.update(params);
        return result.toString();
    }
    @RequestMapping("/delete")
    @ResponseBody
    public String delete(HttpServletRequest req) {
        String  id=req.getParameter("id");
        Map<String,Object> params=new HashMap<>();
        params.put("id",id);
        Integer result=testService.delete(id);
        return result.toString();
    }
}
