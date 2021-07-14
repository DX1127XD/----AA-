package com.example.springboot.controller;

import com.example.springboot.mapper.ActivityMapper;
import com.example.springboot.mapper.GroupMapper;
import com.example.springboot.mapper.MemberMapper;
import com.example.springboot.mapper.UserMapper;
import com.example.springboot.pojo.Activity;
import com.example.springboot.pojo.Group;
import com.example.springboot.pojo.Member;
import com.example.springboot.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@Controller
public class AddController {
    @Autowired
    private ActivityMapper activityMapper;

    @Autowired
    private MemberMapper memberMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private GroupMapper groupMapper;

    @RequestMapping("/AddEvent")
    public String AddEvent(){
        return "addevent";
    }

    @RequestMapping("/Add")
    public String Add(HttpServletResponse response,
                      @RequestParam("place") String place,
                      @RequestParam("time") String time,
                      @RequestParam("events") String events,
                      @RequestParam("consumption") Integer consumption,
                      @RequestParam("son") String son,
                      Model model) throws IOException {
        User user1 = userMapper.queryUserByLogin(1);
        Group group = new Group(groupMapper.queryGroup()+1,place,time,events,consumption,null,0);
        groupMapper.addGroup(group);
        Member member =new Member(memberMapper.queryMember()+1,user1.getName(),user1.getMail(),groupMapper.queryGroup(),son,"团长",100,0);
        memberMapper.addMember(member);
        Activity activity = new Activity(groupMapper.queryGroup(),son);
        activityMapper.addActivity(activity);
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        out.print("<Script>alert('开团成功');window.location.href='main2.html';</Script>");
        out.flush();
        return "main2";
    }

    @RequestMapping("/Add2")

    public String Add2(Model model){
        return "main2";
    }


    @RequestMapping("/Add3")
    public String Add3(@RequestParam("uname") String uname,
                       @RequestParam("upwd") String upwd,
                       Model model, HttpServletResponse response) throws IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        if("Admin".equals(uname)&&"123456".equals(upwd)){
            return "redirect:/AddEvent";
        }
        else{

            out.print("<Script>alert('团长权限验证失败');window.location.href='main.html';</Script>");

        }
        out.flush();
        return "main";
    }
    @RequestMapping("/Add4/{id}")

    public String Add4(@PathVariable("id")Integer id, Model model){
        model.addAttribute("id",id);
        return "List11";
    }

    @RequestMapping("/Add5/{id}")
    public String Add5(@PathVariable("id")Integer id,@RequestParam("son") String son,Model model){
        Activity activity  = new Activity(id,son);
        activityMapper.addActivity(activity);
        return "redirect:/manage/{id}";
    }
}

