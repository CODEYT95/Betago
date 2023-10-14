package com.bnw.beta.controller.member.join;

import com.bnw.beta.service.member.MemberServiceImpl;
import com.bnw.beta.config.vaildation.member.JoinForm;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/member")
public class JoinController {
    private final MemberServiceImpl memberService;

    //약관동의 및 본인 인증폼 보여주기
    @GetMapping("/agree")
    public String memberAgreeForm(JoinForm joinForm) {
        return "member/join/agree";
    }

    //약관동의 및 본인 인증처리
    @PostMapping("/agree")
    public String member(@Valid JoinForm joinForm, BindingResult bindingResult, Model model,
                         @RequestParam("next") String next, @RequestParam("check_3") String check3,
                         @RequestParam("check_4") String check4, @RequestParam("check_5") String check5, HttpSession session) {


        model.addAttribute("name", joinForm.getMember_name());
        model.addAttribute("email", joinForm.getMember_email());
        model.addAttribute("phone", joinForm.getMember_phone());

        session.setAttribute("joinForm", joinForm);
        session.setAttribute("agree3", check3);
        session.setAttribute("agree4", check4);
        session.setAttribute("agree5", check5);
        // 세션에서 agree 값을 가져와서 배열로 저장
        String[] agreedTerms = {
                (String) session.getAttribute("agree3"),
                (String) session.getAttribute("agree4"),
                (String) session.getAttribute("agree5")
        };
        session.setAttribute("agreedTerms", agreedTerms);
        return "/member/join/join";
    }

    //회원가입 처리
    @PostMapping("/join")
    public String memberJoin(@Valid JoinForm joinForm, BindingResult bindingResult, HttpSession session,
                             @RequestParam("member_birth") String birth,Model model) {

        if(bindingResult.hasErrors()) {
            return "/member/join/join";
        }

        //생년월일 session에 담기
        JoinForm agreeDate = (JoinForm) session.getAttribute("joinForm");
        String[] retrievedAgreedTerms = (String[]) session.getAttribute("agreedTerms");
        if (agreeDate != null && retrievedAgreedTerms != null) {
            joinForm.setMember_birth(birth);
            memberService.memberJoin(agreeDate, joinForm, retrievedAgreedTerms);
            session.invalidate();
            return "redirect:/login";
        }
        
       return " ";
    }

    //ID중복체크
    @PostMapping("/idCheck")
    @ResponseBody
    public int idCheck(@RequestParam("id") String id){
        int cnt = memberService.idCheck(id);
        return cnt;
    }

    //이메일 중복체크
    @PostMapping("/emailCheck")
    @ResponseBody
    public int emailCheck(@RequestParam("email") String email){
        int cnt = memberService.emailCheck(email);
        return cnt;
    }

    //핸드폰 중복체크
    @PostMapping("/phoneCheck")
    @ResponseBody
    public int phoneCheck(@RequestParam("phone")String phone){
        int cnt = memberService.phoneCheck(phone);
        return cnt;
    }



    
    //이용약관 팝업창 띄우기
    @GetMapping("/check/{checkId}")
    public String openPopup(@PathVariable String checkId) {
        System.out.println(checkId);
        return "member/join/" + checkId;
    }

}
