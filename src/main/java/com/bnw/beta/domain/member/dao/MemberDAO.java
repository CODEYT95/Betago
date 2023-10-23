package com.bnw.beta.domain.member.dao;

import com.bnw.beta.domain.member.dto.AgreeCheckDTO;
import com.bnw.beta.domain.member.dto.MemberDTO;
import com.bnw.beta.domain.member.dto.RoleDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.time.LocalDate;
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

    //코드 중복체크
    int codeDuplicate(String edu_code);

    //코드 조회
    int codeCheck(String edu_code);

    //question 글등록 유저 아이디
    MemberDTO findUserByUsername(String username);

    //////////멤버 정보 불러오기///////////
    MemberDTO getMemberInfo(String member_id);

    /*회원목록조회 관리자용*/
    List<MemberDTO> memberlist(@Param("startDate") String startDate, @Param("endDate") String endDate, @Param("page") int page, @Param("size") int size, @Param("searchType") String searchType, @Param("searchType2") String searchType2, @Param("searchType3") String searchType3, @Param("keyword") String keyword);

    int count(@Param("startDate") String startDate, @Param("endDate") String endDate, @Param("searchType") String searchType, @Param("searchType2") String searchType2, @Param("searchType3") String searchType3, @Param("keyword") String keyword);
    List<MemberDTO> memberlist();

    /////////멤버 롤 가져오기/////////////김현민
    MemberDTO getRoleById(String member_id);

    /////////로그인 된 아이디 가져오기/////////////김현민
    MemberDTO loginByUsername(String username);

    MemberDTO findIDbyUserName(@Param("name") String name, @Param("email") String email);

   /* MemberDTO findPwbyUserId(@Param("id") String id, @Param("email") String email);
    */
   public void updatePassword(@Param("id") String member_id, @Param("newPassword") String rawPassword);
    MemberDTO findByUserIdAndEmail(@Param("id")String member_id,@Param("email") String email);

    //임시비밀번호 비밀번호 변경
    @Update("UPDATE member SET member_pw = #{newPassword} WHERE member_email = #{email}")
    void updateSetPassword(@Param("email") String email, @Param("newPassword") String password);

    String findPasswordByEmail(String email);

    //회원 license 업데이트
    int updateLicense(@Param("member_id") String member_id);
}
