package com.example.springboot.mapper;

import com.example.springboot.pojo.Activity;
import com.example.springboot.pojo.Group;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface ActivityMapper {
    List<Activity> querySonByGrp(int grp);
    Integer addActivity(Activity activity);
}
