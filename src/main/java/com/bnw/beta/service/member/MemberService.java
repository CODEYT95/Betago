package com.bnw.beta.service.member;

import com.bnw.beta.config.vaildation.member.JoinForm;
import com.bnw.beta.domain.common.paging.MemberPageDTO;
import com.bnw.beta.domain.member.dto.MemberDTO;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface MemberService {

        //회원가입(본인인증)
        public default String memberJoin(JoinForm agreeDate, JoinForm nextJoinForm, String[] retrievedAgreedTerms) {
                return "sucess";
        }

        //아이디 중복체크
        public int idCheck(String id);

        //이메일 중복체크
        public int emailCheck(String email);

        //핸드폰 중복체크
        public int phoneCheck(String phone);

        //Question 글작성 회원 아이디 정보
        Optional<MemberDTO> getUser(String username);

        //////////멤버 정보 불러오기///////////
        MemberDTO getMemberInfo(String member_id);
        /*회원 목록보기*/
        //public MemberPageDTO memberlist(int pageNum, int size, String searchType, String keyword);

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
















        /*현민님꺼
        private final MemberMapper memberMapper;
        private final PasswordEncoder passwordEncoder;


        @Autowired
        public MemberService(MemberMapper memberMapper, PasswordEncoder passwordEncoder) {
                this.memberMapper = memberMapper;
                this.passwordEncoder = passwordEncoder;
        }

        @Transactional
        public void registerMember(MemberDTO memberDTO) {

                String encodedPassword = passwordEncoder.encode(memberDTO.getMember_pw());
                memberDTO.setMember_pw(encodedPassword);

                memberMapper.insertMember(memberDTO);
        }
        */
}