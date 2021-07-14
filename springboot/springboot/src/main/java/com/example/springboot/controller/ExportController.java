package com.example.springboot.controller;

import com.example.springboot.mapper.MemberMapper;
import com.example.springboot.pojo.Member;
import com.example.springboot.util.WebExcelUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
public class ExportController {
    @Autowired
    MemberMapper memberMapper;

    @RequestMapping("/export")
    public void export(HttpServletResponse response) {
        List<Member> list = memberMapper.list();

        WebExcelUtil.exportExcel(list, "活动账单", "sheet1", Member.class, "活动账单.xls", response);
    }
}
