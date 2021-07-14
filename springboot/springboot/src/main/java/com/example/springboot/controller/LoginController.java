package com.example.springboot.controller;

import com.example.springboot.mapper.UserMapper;
import com.example.springboot.pojo.Group;
import com.example.springboot.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.List;

@Controller
public class LoginController {
    public boolean flag;

    @Autowired
    UserMapper userMapper;

    @RequestMapping("/user/login")
    public String login(@RequestParam("username") String username, @RequestParam("password") String password, Model model){
        flag=false;
        for(int i=1;i<=userMapper.queryUser();i++){

            if(!userMapper.queryUsernameById(i).equals(username)){
                continue;
            }
            flag=true;
            break;
        }
        if(!flag){
            model.addAttribute("msg","用户名或者密码错误！");
            return "index";
        }
        if(userMapper.queryUserByLogin(1)!=null){
            User user1 = userMapper.queryUserByLogin(1);
            User user3 = new User(user1.getId(),user1.getUsername(),user1.getName(),user1.getPassword(),0,user1.getGrp(),user1.getMail());
            userMapper.updateUser(user3);
        }
        String passwords = userMapper.queryPasswordByUsername(username);
        if(passwords.equals(password)){
            User user = userMapper.queryUserByUsername(username);
            User user2 = new User(user.getId(),user.getUsername(),user.getName(),user.getPassword(),1,user.getGrp(),user.getMail());
            userMapper.updateUser(user2);
            return "main2";
        }
        else{
            model.addAttribute("msg","用户名或者密码错误！");
            return "index";
        }
    }

    @RequestMapping("/exit")
    public String Exit(HttpServletResponse response,Model model) throws IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        out.print("<Script>if(confirm('确认退出吗？')){window.location.href='index.html';} </Script>");
        out.flush();
        return "main2";
    }

}
