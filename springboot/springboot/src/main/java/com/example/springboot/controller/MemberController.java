package com.example.springboot.controller;

import com.example.springboot.mapper.MemberMapper;
import com.example.springboot.pojo.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class MemberController {
    @Autowired
    private MemberMapper memberMapper;

    @GetMapping("/queryMemberList")
    public List<Member> queryMemberList(){
        List<Member> memberList = memberMapper.queryMemberList();
        return memberList;
    }
}
