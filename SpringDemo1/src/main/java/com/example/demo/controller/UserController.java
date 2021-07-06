package com.example.demo.controller;

import com.example.demo.config.AppFinal;
import com.example.demo.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.util.ClassUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.UUID;

/**
 * ClassName:UserController
 * Package:com.example.demo.controller
 * Description:
 *
 * @Author:HP
 * @date:2021/6/28 15:27
 */
@RequestMapping("/user")
@Controller
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
    @ResponseBody
    public Object login(User user, HttpServletRequest req) {
        HashMap<String,Object> map = new HashMap<>();
        int status = -1;
        String msg = "未知错误";
        String data = "登录失败";

        if(user != null && user.getUsername().equals("root") && user.getPassword().equalsIgnoreCase("root")) {
            HttpSession session = req.getSession();
            session.setAttribute(AppFinal.USERINFO_SESSIONKEY,user);
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

    @RequestMapping("/login3")
    @ResponseBody // 当前方法返回的为数据而非视图
    public Object login3(@RequestParam(name = "name") String username,
                         String password,
                         HttpServletRequest request) {
        // 返回的对象
        HashMap<String, Object> map = new HashMap<>();
        int status = -1; // 非正常返回
        String msg = "未知错误";
        String data = "登录失败";
        map.put("status", status);
        map.put("msg", msg);
        map.put("data", username);
        return map;
    }

    @RequestMapping("/reg")
    @ResponseBody
    public Object regin(String username,String password,@RequestPart MultipartFile file) throws IOException {
        //1.动态获取当前项目的路劲
        String path = ClassUtils.getDefaultClassLoader().getResource("static").getPath();
        path += "/upload/";
        log.info("path:" + path);
        //2.文件名

        String fileType = file.getOriginalFilename();
        fileType = fileType.substring(fileType.lastIndexOf("."));
        String fileName = UUID.randomUUID().toString() + fileType;

        //3.将文件保存到服务器
        file.transferTo(new File(path + fileName));
        return "redirect:/login.html";
    }
}
