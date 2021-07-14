package com.example.springboot.pojo;


import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Member {
    @Excel(name = "ID", width = 25,orderNum = "0")
    private Integer id;
    @Excel(name = "姓名", width = 25,orderNum = "0")
    private String name;
    @Excel(name = "电子邮件", width = 25,orderNum = "0")
    private String email;
    @Excel(name = "所在团ID", width = 25,orderNum = "0")
    private Integer grp;
    @Excel(name = "子项目", width = 25,orderNum = "0")
    private String son;
    @Excel(name = "权限", width = 25,orderNum = "0")
    private String leader;
    @Excel(name = "消费", width = 25,orderNum = "0")
    private Integer expense;
    @Excel(name = "缴费", width = 25,orderNum = "0")
    private Integer money;



}
