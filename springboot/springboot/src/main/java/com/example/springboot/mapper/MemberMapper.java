package com.example.springboot.mapper;

import com.example.springboot.pojo.Member;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface MemberMapper {
    List<Member> queryMemberList();
    Member queryMemberById(int id);
    Integer queryMember();
    List<Member> queryMemberByGroup(int group);
    List<Member> queryMemberByName(String name);
    Integer addMember(Member member);
    Integer countMember(Integer grp);
    Integer updateMember(Member member);
    Integer deleteMember(Member member);
    List<Member> list();

}
