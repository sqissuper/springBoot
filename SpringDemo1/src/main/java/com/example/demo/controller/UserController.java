package com.example.demo.controller;

import com.example.demo.model.User;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;

/**
 * ClassName:UserController
 * Package:com.example.demo.controller
 * Description:
 *
 * @Author:HP
 * @date:2021/6/28 15:27
 */

@Controller
@RequestMapping("/user")
@Slf4j
public class UserController {
//    private Logger logger = LoggerFactory.getLogger(UserController.class);

//    @RequestMapping("/param1")
//    public String param1(@RequestBody User user) {
//        return "姓名：" + user.getName() + ",账户名" + user.getUsername() + ",密码：" + user.getPassword();
//    }
//    @RequestMapping("/say")
//    @ResponseBody
//    public String getIndex(String name) {
//        if(name == null || name.equals("")) {
//            log.error("级别：error");
//        }
//        log.debug("级别：debug");
//        log.info("级别：info");
//        log.warn("级别：warn");
//        log.trace("级别：track");
//        return "hello spring";
//    }

    @RequestMapping("/login")
    public Object login(User user, HttpServletRequest req) {
        HashMap<String,Object> map = new HashMap<>();
        int status = -1;
        String msg = "未知错误";
        String data = "登录失败";

        if(user != null && user.getUsername().equals("root") && user.getPassword().equalsIgnoreCase("root")) {
            HttpSession session = req.getSession();
            session.setAttribute("userInfo",user);
            status = 0;
            msg = "";
            data = "登录成功";
        }else {
            status = 0;
            msg = "用户名或密码错误";
            data = "登录失败";
        }
        map.put("status",status);
        map.put("msg",msg);
        map.put("data",data);
        return map;
    }
}
