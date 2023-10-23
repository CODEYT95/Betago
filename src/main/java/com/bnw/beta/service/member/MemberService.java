package com.bnw.beta.service.member;

import com.bnw.beta.config.vaildation.member.JoinForm;
import com.bnw.beta.domain.common.paging.MemberPageDTO;
import com.bnw.beta.domain.member.dto.MemberDTO;
import com.bnw.beta.domain.member.dto.RoleDTO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Date;
import java.util.Optional;

@Service
public interface MemberService {

        //회원가입(본인인증)
        public default String memberJoin(JoinForm agreeDate, JoinForm nextJoinForm, String[] retrievedAgreedTerms) {
                return "sucess";
        }

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



        //Question 글작성 회원 아이디 정보
        Optional<MemberDTO> getUser(String username);

        //////////멤버 정보 불러오기///////////
        MemberDTO getMemberInfo(String member_id);
        /*회원 목록보기*/
        public MemberPageDTO memberlist(Date startDate, Date endDate, int pageNum, int size, @Param("searchType") String searchType, @Param("searchType2") String searchType2, @Param("searchType3") String searchType3, String keyword);

        public int count(Date startDate, Date endDate, @Param("searchType") String searchType, @Param("searchType2") String searchType2, @Param("searchType3") String searchType3, String keyword);
        //////////멤버 role 불러오기/////////// 김현민
        public String getRoleById(String member_id);

        //////////로그인된 사용자불러오기/////////// 김현민
        MemberDTO loginByUsername(String username);

        //////// 회원 아이디 찾기//////////////김현민
        MemberDTO findID(String name, String email);

        //////// 회원 비번 찾기//////////////김현민
       /* MemberDTO findPw(String id, String email);*/

        //////// 임시 비번 바꿔//////////////김현민
        void updatePassword(String member_id, String encryptedPassword);


        void changeUserPassword(String email, String newPassword);

        boolean checkPassword(String email, String currentPassword);

        //회원 license 업데이트
        int updateLicense(@Param("member_id") String member_id);

}