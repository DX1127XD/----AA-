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
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Controller
public class list2Controller {
    @Autowired
    ActivityMapper activityMapper;

    @Autowired
    JavaMailSenderImpl mailSender;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private GroupMapper groupMapper;
    @Autowired
    private MemberMapper memberMapper;

    @RequestMapping("/list2")
    public String list2(Model model){

        Collection<Group> groups1 = groupMapper.queryGroupByFinish(0);
        model.addAttribute("grps",groups1);
        Collection<Group> groups2 = groupMapper.queryGroupByFinish(1);
        model.addAttribute("grps2",groups2);
        return "List2";

    }
    @RequestMapping("/grp/{id}")
    public String modify(@PathVariable("id")Integer id,Model model){
        User user1 = userMapper.queryUserByLogin(1);
        User user3 = new User(user1.getId(),user1.getUsername(),user1.getName(),user1.getPassword(),1,id,user1.getMail());
        userMapper.updateUser(user3);
        Collection<Member> members = memberMapper.queryMemberByGroup(id);
        model.addAttribute("mems",members);
        Collection<Activity> activity = activityMapper.querySonByGrp(id);
        model.addAttribute("acts",activity);
        return "List8";
    }
    @RequestMapping("/list4/{son}")
    public String list4(@PathVariable("son")String son,HttpServletResponse response,Model model) throws IOException {
        boolean flag=false;
        User user = userMapper.queryUserByLogin(1);
        List<Member> members1 = memberMapper.queryMemberList();
        for(Member member:members1){

            if(member.getName().equals(user.getName())){
                if(member.getGrp()==user.getGrp()){
                    response.setContentType("text/html;charset=UTF-8");
                    PrintWriter out = response.getWriter();
                    out.print("<Script>alert('已加入团，不可重复加入');window.location.href='/main2.html';</Script>");
                    out.flush();
                    flag=true;
                }
            }
        }
        if(flag==false){
            int a  = memberMapper.queryMember();
            Member member = new Member(a+1,user.getName(),user.getMail(),user.getGrp(),son,"团员",100,0);
            memberMapper.addMember(member);
            Collection<Member> members = memberMapper.queryMemberList();
            model.addAttribute("mems",members);
            response.setContentType("text/html;charset=UTF-8");
            PrintWriter out = response.getWriter();
            out.print("<Script>alert('加入成功');window.location.href='/main2.html';</Script>");
            out.flush();

        }
        return "main2";

    }

    @RequestMapping("/delete/{id}")
    public String delete(HttpServletResponse response,@PathVariable("id")Integer id,Model model) throws IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        out.print("<Script>alert('删除成功');window.location.href='/main2.html';</Script>");
        out.flush();
        groupMapper.deleteGroup(id);
        User user1 = userMapper.queryUserByLogin(1);
        Collection<Member> members=memberMapper.queryMemberByName(user1.getName());
        for(Member member :members){
            if(member.getGrp()==id){
                memberMapper.deleteMember(member);
            }
        }
        return "main";
    }
    @RequestMapping("/update/{id}")
    public String update(@PathVariable("id")Integer id,Model model){
        Group group = groupMapper.queryGroupById(id);
        model.addAttribute("grp",group);
        return "update";
    }
    @RequestMapping("/update")
    public String update2(@RequestParam("id") Integer id,
                          @RequestParam("place") String place,
                         @RequestParam("time") String time,
                         @RequestParam("events") String events,
                          @RequestParam("consumption") Integer consumption,
                         Model model){
        Group group = new Group(id,place,time,events,consumption,null,0);
        groupMapper.updateGroup(group);
        return "redirect:/list";
    }
    @RequestMapping("/list5")
    public String list5(Model model){

        Collection<Group> groups = groupMapper.queryGroupList();
        model.addAttribute("grps",groups);
        return "List5";

    }

    @RequestMapping("/list6/{id}")
    public String list6(@PathVariable("id")Integer id,Model model){
        Collection<Member> members = memberMapper.queryMemberByGroup(id);
        model.addAttribute("mems",members);
        return "List6";

    }
    @RequestMapping("/pay/{id}")
    public String pay(@PathVariable("id")Integer id,Model model){
        Member member = memberMapper.queryMemberById(id);
        model.addAttribute("mem",member);
        return "pay";
    }
    @RequestMapping("/list6")
    public String list6(Model model){
        List<Member> members1= memberMapper.queryMemberList();
        for(Member member:members1){
            int a = memberMapper.countMember(member.getGrp());
            int b =groupMapper.queryGroupById(member.getGrp()).getConsumption();
            Member member1 = new Member(member.getId(),member.getName(),member.getEmail(),member.getGrp(),member.getSon(),member.getLeader(),b/a,member.getMoney());
            memberMapper.updateMember(member1);
        }
        Collection<Member> members = memberMapper.queryMemberList();
        model.addAttribute("mems",members);
        return "List4";

    }
    @RequestMapping("/register")
    public String register(Model model){
        return "register";

    }
    @RequestMapping("/register2")
    public String register2(HttpServletResponse response,@RequestParam("username") String username, @RequestParam("name") String name,@RequestParam("password") String password,@RequestParam("mail") String mail,Model model) throws IOException {
        if(userMapper.queryUserByLogin(1)!=null){
            User user1 = userMapper.queryUserByLogin(1);
            User user3 = new User(user1.getId(),user1.getUsername(),user1.getName(),user1.getPassword(),0,user1.getGrp(),user1.getMail());
            userMapper.updateUser(user3);
        }
        User  user = new User(userMapper.queryUser()+1,username,name,password,1,0,mail);
        userMapper.addUser(user);
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        out.print("<Script>alert('注册成功');window.location.href='main2.html';</Script>");
        out.flush();
        return "main2";

    }
    @RequestMapping("/pay1/{id}")
    public String pay1(@PathVariable("id")Integer id,@RequestParam("docVlGender") String docVlGender,@RequestParam("other") Integer other,HttpServletResponse response,Model model) throws IOException {
        Member member1 = memberMapper.queryMemberById(id);

        if(docVlGender.equals("10")){
            Member member2=new Member(member1.getId(),member1.getName(),member1.getEmail(),member1.getGrp(),member1.getSon(),member1.getLeader(),member1.getExpense(),member1.getMoney()+10);
            memberMapper.updateMember(member2);
        }
        if(docVlGender.equals("20")){
            Member member2=new Member(member1.getId(),member1.getName(),member1.getEmail(),member1.getGrp(),member1.getSon(),member1.getLeader(),member1.getExpense(),member1.getMoney()+20);
            memberMapper.updateMember(member2);
        }
        if(docVlGender.equals("50")){
            Member member2=new Member(member1.getId(),member1.getName(),member1.getEmail(),member1.getGrp(),member1.getSon(),member1.getLeader(),member1.getExpense(),member1.getMoney()+50);
            memberMapper.updateMember(member2);
        }
        if(docVlGender.equals("0")){
            Member member2=new Member(member1.getId(),member1.getName(),member1.getEmail(),member1.getGrp(),member1.getSon(),member1.getLeader(),member1.getExpense(),member1.getMoney()+other);
            memberMapper.updateMember(member2);
        }



        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        out.print("<Script>alert('缴费成功');window.location.href='/main2.html';</Script>");
        out.flush();
        return "main2";

    }
    @RequestMapping("/query1")
    public String query1(HttpServletResponse response,Model model) throws IOException {
        User user1 = userMapper.queryUserByLogin(1);
        Collection<Member> members=memberMapper.queryMemberByName(user1.getName());
        if(members.isEmpty()){
            response.setContentType("text/html;charset=UTF-8");
            PrintWriter out = response.getWriter();
            out.print("<Script>alert('您尚未加入任何一个团');window.location.href='main2.html';</Script>");
            out.flush();
            return "main2";
        }
        else{
            model.addAttribute("mems",members);
            return "List6";
        }
    }
    @RequestMapping("/query2")
    public String query2(HttpServletResponse response,Model model) throws IOException {
        User user1 = userMapper.queryUserByLogin(1);
        Collection<Member> members=memberMapper.queryMemberByName(user1.getName());
        List<Group> groups=new ArrayList<>();
        for(Member member:members){
            if(member.getLeader().equals("团长")){
                int a = member.getGrp();
                Group group =groupMapper.queryGroupById(a);
                groups.add(group);
            }
        }
        if(groups.isEmpty()){
            response.setContentType("text/html;charset=UTF-8");
            PrintWriter out = response.getWriter();
            out.print("<Script>alert('您尚未创建任何一个团');window.location.href='main2.html';</Script>");
            out.flush();
            return "main2";
        }
        model.addAttribute("grps",groups);
        return "List";
    }
    @RequestMapping("/manage/{id}")
    public String manage(@PathVariable("id")Integer id,Model model){
        Collection<Member> members = memberMapper.queryMemberByGroup(id);
        model.addAttribute("mems",members);
        Collection<Activity> activity = activityMapper.querySonByGrp(id);
        model.addAttribute("acts",activity);
        model.addAttribute("id",id);
        return "List10";

    }

}
