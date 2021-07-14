package com.example.springboot.mapper;


import com.example.springboot.pojo.Member;
import com.example.springboot.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface UserMapper {
    List<User> queryUserList();
    User queryUserByUsername(String username);
    String queryPasswordByUsername(String username);
    User queryUserByLogin(int login);
    String queryUsernameById(int id);
    Integer queryUser();
    Integer queryIdByUsername(String username);
    Integer addUser(User user);
    Integer updateUser(User user);
    Integer deleteUser(User user);

}
