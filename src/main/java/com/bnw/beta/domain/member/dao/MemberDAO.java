package com.bnw.beta.domain.member.dao;

import com.bnw.beta.domain.member.dto.AgreeCheckDTO;
import com.bnw.beta.domain.member.dto.MemberDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Repository;

import java.util.List;

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

    //////////멤버 정보 불러오기///////////
    MemberDTO getMemberInfo(String member_id);

    /*회원목록조회 관리자용*/
    List<MemberDTO> memberlist(@Param("page") int page, @Param("size") int size, @Param("searchType") String searchType, @Param("searchType2") String searchType2, @Param("searchType3") String searchType3, @Param("keyword") String keyword);

    int count(@Param("searchType") String searchType, @Param("searchType2") String searchType2, @Param("searchType3") String searchType3, @Param("keyword") String keyword);
    List<MemberDTO> memberlist();

    /////////멤버 롤 가져오기/////////////김현민
    MemberDTO getRoleById(String member_id);

    /////////로그인 된 아이디 가져오기/////////////김현민
    MemberDTO loginByUsername(String username);
}
