package com.bnw.beta.service.member;

import com.bnw.beta.config.security.SecurityConfig;
import com.bnw.beta.config.vaildation.member.JoinForm;
import com.bnw.beta.domain.common.paging.MemberPageDTO;
import com.bnw.beta.domain.member.dao.MemberDAO;
import com.bnw.beta.domain.member.dto.AgreeCheckDTO;
import com.bnw.beta.domain.member.dto.MemberDTO;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberDAO memberDAO;


    // 회원가입(본인 인증 후 나머지 정보 입력)
    @Override
    public String memberJoin(JoinForm agreeDate, JoinForm joinForm, String[] retrievedAgreedTerms) {
        {
            //회원 정보 저장
            HttpSession session = null;
            AgreeCheckDTO agreeCheckDTO;
            //시큐리티 비밀번호 암호화
            SecurityConfig securityConfig = new SecurityConfig();
            String password = securityConfig.encodePWD().encode(joinForm.getMember_pw());
            //회원 정보 저장
            MemberDTO memberDTO = new MemberDTO();
            memberDTO.setMember_name(agreeDate.getMember_name());
            memberDTO.setMember_email(agreeDate.getMember_email());
            memberDTO.setMember_phone(joinForm.getMember_phone());
            memberDTO.setMember_id(joinForm.getMember_id());
            memberDTO.setMember_pw(password);
            memberDTO.setMember_birth(joinForm.getMember_birth());
            memberDTO.setRole(joinForm.getRole());
            memberDTO.setMember_gender(joinForm.getMember_gender());
            memberDTO.setMember_tell(joinForm.getMember_tell());
            memberDTO.setMember_agreeM(joinForm.getMember_agreeM());
            memberDTO.setMember_agreeP(joinForm.getMember_agreeP());
            int info = memberDAO.memberJoin(memberDTO);

            //약관동의(선택) 저장
            for (String agreedTerm : retrievedAgreedTerms) {
                System.out.println(agreedTerm);
                if (!agreedTerm.equals("null")) {
                    int termsRecord = Integer.parseInt(agreedTerm);
                    agreeCheckDTO = new AgreeCheckDTO();
                    agreeCheckDTO.setMember_id(joinForm.getMember_id());
                    agreeCheckDTO.setTerms_record((long) termsRecord);
                    memberDAO.memberAgree(agreeCheckDTO);
                } else {
                    System.out.println("agreedTerm is null. Skipping...");
                }
            }
            System.out.println(info);
        }
        return " ";
    }

    //아이디 중복체크
    @Override
    public int idCheck(String id){
        int cnt = memberDAO.idCheck(id);
        System.out.println("cnt:" + cnt);
        return cnt;
    }

    //이메일 중복체크
    @Override
    public int emailCheck(String email){
        int cnt = memberDAO.emailCheck(email);
        return cnt;
    }

    //핸드폰 중복체크
    @Override
    public int phoneCheck(String phone){
        int cnt = memberDAO.phoneCheck(phone);
        return cnt;
    }

    //글등록 유저 아이디 가져오기
    public Optional<MemberDTO> getUser(String username){
        MemberDTO member = memberDAO.findUserByUsername(username);  // 메서드 호출 변경
        return Optional.ofNullable(member);

    }

    //////////멤버 정보 불러오기///////////
    public MemberDTO getMemberInfo(String member_id){

        System.out.println(memberDAO.getMemberInfo(member_id).getMember_name());

        return memberDAO.getMemberInfo(member_id);}

    /*회원 목록보기*/
    @Override
    public MemberPageDTO memberlist(int pageNum, int size, String searchType, String keyword) {
        if(pageNum <= 0) {
            pageNum = 1;
        }
        System.out.println("size :" + size);
        int offset = (pageNum-1) * size;
        List<MemberDTO> memberlist = memberDAO.memberlist(offset, size, searchType, keyword);
        System.out.println("size2 :" + size);
        int listCount = memberDAO.count(searchType, keyword);

        MemberPageDTO memberPageDTO = new MemberPageDTO(listCount, pageNum, size, memberlist);
        memberPageDTO.setListCount(listCount);

        return memberPageDTO;
    }

}