package com.example.springboot.mapper;

import com.example.springboot.pojo.Group;
import com.example.springboot.pojo.Member;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface GroupMapper {
    List<Group> queryGroupList();
    Group queryGroupById(int id);
    Integer queryGroup();
    Integer addGroup(Group group);
    Integer updateGroup(Group group);
    Integer deleteGroup(int id);
    List<Group> queryGroupByFinish(int finish);


}