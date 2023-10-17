package com.bnw.beta.controller.member.login;

import com.bnw.beta.config.vaildation.member.PasswordUtils;
import com.bnw.beta.domain.member.dao.MemberDAO;
import com.bnw.beta.domain.member.dto.MemberDTO;
import com.bnw.beta.service.member.MailSendServiceImpl;
import com.bnw.beta.domain.member.dao.MemberDAO;
import com.bnw.beta.domain.member.dto.MemberDTO;
import com.bnw.beta.service.member.MailSendServiceImpl;
import com.bnw.beta.service.member.MemberService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;


@Controller
public class MemberController {

    private final MemberService memberService;
    @Autowired
    private MailSendServiceImpl mailSendService;
    @Autowired
    private MemberDAO memberDAO;

    @Autowired
    public MemberController(MemberService memberService, MailSendServiceImpl mailSendService, MemberDAO memberDAO) {
        this.memberService = memberService;
        this.mailSendService = mailSendService;
        this.memberDAO = memberDAO;
    }

    //시큐리티 통해서 로그인폼 보여주기
    @GetMapping("/login")
    public String login(Principal principal, HttpSession session) {
        System.out.println("실패");
        return "member/login/login_form";
    }



    @GetMapping("/findId")
    public String findId() {
        return "/member/login/findId";
    }

    @PostMapping("/findId")
    public String findId(@RequestParam("name") String name,
                         @RequestParam("email") String email, Model model) {
        // 서비스 계층을 통해 아이디 찾기 로직 처리
        MemberDTO memberDTO = memberService.findID(name, email);
        if (memberDTO != null) {
            // 아이디 찾기에 성공한 경우, 결과를 모델에 추가
            model.addAttribute("memberDTO", memberDTO);
        } else {
            // 실패한 경우 (일치하는 사용자 없음)
            model.addAttribute("findIdFail", true);
        }
        return "member/login/findId";
    }


    @GetMapping("/findPw")
    public String findPw() {
        return "/member/login/findPw";
    }

    @PostMapping("/findPw2")
    public ResponseEntity<?> findPassword(@RequestParam String id, @RequestParam String email) {
        MemberDTO memberDTO = memberDAO.findByUserIdAndEmail(id, email);
        if (memberDTO == null) {
            return new ResponseEntity<>("아이디 또는 이메일이 잘못되었습니다.", HttpStatus.NOT_FOUND);
        }

        String tempPassword = generateTemporaryPassword(); // 임시 비밀번호 생성 로직
        String encryptedPassword = passwordEncoder.encode(tempPassword); // 비밀번호 암호화
        memberDAO.updatePassword(id, encryptedPassword);

        // 사용자에게 이메일 전송 로직 (구현에 따라 서비스 클래스 안에서 처리할 수도 있음)
        mailSendService.sendTemporaryPassword(email, tempPassword);

        return new ResponseEntity<>("임시 비밀번호가 이메일로 전송되었습니다.", HttpStatus.OK);
    }

    // 임시 비밀번호 생성 로직 메소드
    private String generateTemporaryPassword() {
        return PasswordUtils.generateTemporaryPassword(10); }




    /*@PostMapping("/findPw")
    public String findPw(@RequestParam("id") String id,
                         @RequestParam("email") String email, Model model) {
        // 서비스 계층을 통해 아이디 찾기 로직 처리
        MemberDTO memberDTO = memberService.findPw(id, email);
        if (memberDTO != null) {
            // 아이디 찾기에 성공한 경우, 결과를 모델에 추가
            model.addAttribute("memberDTO", memberDTO);
        } else {
            // 실패한 경우 (일치하는 사용자 없음)
            model.addAttribute("findPwFail", true);
        }
        return "member/login/findPw";
    }*/



    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping({"","/"})
    public String index(Principal principal, HttpSession session) {

        if(principal != null){
            session.setAttribute("member_no", memberService.getMemberInfo(principal.getName()).getMember_no());
            session.setAttribute("member_name", memberService.getMemberInfo(principal.getName()).getMember_name());
            session.setMaxInactiveInterval(1800);
        }
        System.out.println("성공");

        System.out.println(session.getAttribute("member_no"));
        return "main/main"; //메인페이지로 설정
    }

    @GetMapping("/loginForm") //기본주소로 설정시 시큐리티가 주소를 낚아채지만 SecurtiyConfig생성후 폼으로 정상 작동
    public String loginForm(){
        return "member/login/login_form"; //<-.html로 이동해라
    }

    @GetMapping("/joinForm")
    public String joinForm2(){
        return "member/join/join_form"; //<-.html로 이동해라
    }

    @GetMapping("/user")
    public @ResponseBody String user() {
        return "user";
    }

    @GetMapping("/admin")
    public @ResponseBody String admin() {
        return "admin";
    }
/*
    유진님 회원가입부분 폼
    @GetMapping("/join")
    public String join(**User user) {
    user.setRole("ROLE_USER");
    String rawPassword=user.getPassword();
    String encPassword=passwordEncoder.encode(rawPassword);
    userRepository.save(user);
    return "redirect:/loginForm";
    }*/

}
