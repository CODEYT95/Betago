package com.bnw.beta.domain.member.dao;

import com.bnw.beta.domain.member.dto.AgreeCheckDTO;
import com.bnw.beta.domain.member.dto.MemberDTO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface MemberDAO {

    //회원가입(약관동의)
    void memberAgree(AgreeCheckDTO agreeCheckDTO);

    //회원가입(정보입력)
    int memberJoin(MemberDTO memberDTO);

    //아이디 중복체크
    int idCheck(String id);

    //이메일 중복체크
    int emailCheck(String email);

    //핸드폰 중복체크
    int phoneCheck(String phone);

    //question 글등록 유저 아이디
    MemberDTO findUserByUsername(String username);

}
