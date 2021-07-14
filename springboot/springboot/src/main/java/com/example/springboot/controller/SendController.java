package com.example.springboot.controller;


import com.example.springboot.mapper.GroupMapper;
import com.example.springboot.mapper.MemberMapper;
import com.example.springboot.mapper.UserMapper;
import com.example.springboot.pojo.Group;
import com.example.springboot.pojo.Member;
import com.example.springboot.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Controller
public class SendController {
    @Autowired
    JavaMailSenderImpl mailSender;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private GroupMapper groupMapper;
    @Autowired
    private MemberMapper memberMapper;

    @RequestMapping("/send")
    public String send(@RequestParam("uname") String uname,
                       @RequestParam("upwd") String upwd,
                       Model model, HttpServletResponse response) throws IOException {

        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        if("Admin".equals(uname)&&"123456".equals(upwd)){
            return "redirect:/send2";
        }
        else{

            out.print("<Script>alert('团长权限验证失败');window.location.href='main.html';</Script>");

        }
        out.flush();
        return "";
    }
    @RequestMapping("/send2")
    public String send2(HttpServletResponse response,Model model) throws IOException {
        User user1 = userMapper.queryUserByLogin(1);
        Collection<Member> members=memberMapper.queryMemberByName(user1.getName());
        List<Member> members2=new ArrayList<>();
        for(Member member:members){
            if(member.getLeader().equals("团长")){
                int a = member.getGrp();
                Collection<Member> members3 = memberMapper.queryMemberByGroup(a);
                for(Member member2:members3){
                    members2.add(member2);
                }
            }
        }
        if(members2.isEmpty()){
            response.setContentType("text/html;charset=UTF-8");
            PrintWriter out = response.getWriter();
            out.print("<Script>alert('您尚未创建任何一个团');window.location.href='main2.html';</Script>");
            out.flush();
            return "main2";
        }
        model.addAttribute("mems",members2);
        return "List7";
    }
    @RequestMapping("/send3/{id}")
    public String send3(@PathVariable("id")Integer id,HttpServletResponse response,Model model) throws IOException {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setSubject("活动通知");
        mailMessage.setText("您已成功入团，可按时参加活动");
        mailMessage.setText("请及时缴纳活动费用");
        mailMessage.setText("活动费用可登录官网101.132.177.73查看");
        mailMessage.setTo(memberMapper.queryMemberById(id).getEmail());
        mailMessage.setFrom("2986045676@qq.com");
        mailSender.send(mailMessage);
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        out.print("<Script>alert('发送成功');window.location.href='/main2.html';</Script>");
        out.flush();
        return "main2";
    }
}
