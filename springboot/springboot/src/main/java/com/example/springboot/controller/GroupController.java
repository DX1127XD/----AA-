package com.example.springboot.controller;

import com.example.springboot.mapper.GroupMapper;
import com.example.springboot.mapper.MemberMapper;
import com.example.springboot.pojo.Group;
import com.example.springboot.pojo.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class GroupController {
    @Autowired
    private GroupMapper groupMapper;

    @GetMapping("/queryGroupList")
    public List<Group> queryGroupList(){
        List<Group> groupList = groupMapper.queryGroupList();
        return groupList;
    }
}
