package com.example.demo.controller;

import com.example.demo.config.AppFinal;
import com.example.demo.mapper.UserMapper;
import com.example.demo.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private UserMapper userMapper;

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


    @RequestMapping("/reg")
    @ResponseBody
    public Object regin(String username,String password,@RequestPart MultipartFile file) throws IOException {
        //1.动态获取当前项目的路劲
        String path = ClassUtils.getDefaultClassLoader().getResource("static").getPath();
        path += AppFinal.IMAG_PATH;
        log.info("path:" + path);
        //2.文件名

        String fileType = file.getOriginalFilename();
        fileType = fileType.substring(fileType.lastIndexOf("."));
        String fileName = UUID.randomUUID().toString() + fileType;

        //3.将文件保存到服务器
        file.transferTo(new File(path + fileName));

        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setPhoto(AppFinal.IMAG_PATH + fileName);
        int res = userMapper.addUser(user);

        if(res > 0) {
            return "redirect:/login.html";
        }
        return "redirect:/regin.html";
    }
}
