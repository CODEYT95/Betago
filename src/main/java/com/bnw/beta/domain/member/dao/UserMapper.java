package com.bnw.beta.domain.member.dao;


import com.bnw.beta.domain.member.dto.MemberDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface UserMapper {
    @Select("SELECT " +
            "member_no, " +
            "member_id, " +
            "member_pw, " +
            "member_name, " +
            "member_birth, " +
            "member_email, " +
            "member_gender, " +
            "member_phone, " +
            "member_tell, " +
            "member_isshow, " +
            "member_agree_m, " +
            "member_agree_p, " +
            "role " +
            "FROM member " +
            "WHERE member_id = #{memberId}")
    MemberDTO findByMemberId(String memberId);
}
