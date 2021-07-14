package com.example.springboot.controller;


import com.example.springboot.mapper.GroupMapper;
import com.example.springboot.pojo.Group;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Collection;

@Controller
public class ListController {
    @Autowired
    private GroupMapper groupMapper;


    @RequestMapping("/list")
    public String list(Model model){
        Collection<Group> groups = groupMapper.queryGroupList();
        model.addAttribute("grps",groups);
        return "List";
    }
}
