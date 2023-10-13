package com.bnw.beta.controller.guide;


import com.bnw.beta.config.vaildation.question.AnswerForm;
import com.bnw.beta.domain.guide.dao.QuestionDAO;
import com.bnw.beta.domain.guide.dto.QuestionDTO;
import com.bnw.beta.domain.member.dto.MemberDTO;
import com.bnw.beta.service.guide.answer.AnswerService;
import com.bnw.beta.service.guide.question.QuestionService;
import com.bnw.beta.service.guide.question.QuestionServiceImpl;
import com.bnw.beta.service.member.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/answer")
@RequiredArgsConstructor
public class AnswerController {

    @Autowired
    private final QuestionServiceImpl questionService;
    private final MemberService memberService;
    private final AnswerService answerService;





    @PreAuthorize("isAuthenticated()")
    @PostMapping("/add/{qna_no}")
    public String addAnswer(@PathVariable("qna_no") int qna_no, Model model,
                            @Valid AnswerForm answerForm, BindingResult bindingResult,
                            Principal principal){
        System.out.println("댓글시작"+qna_no);

        if(bindingResult.hasErrors()){
            /*model.addAttribute("answerForm", answerForm);*/
            return "guide/question/question_list";
        }

        String role = memberService.getRoleById(principal.getName());
        if(!"ROLE_ADMIN".equals(role)){
            System.out.println("권리자권한이 없다 현재 현재 권한은 ="+role);
            return "error/notAuthorized";
        }
        System.out.println("현재 권한은"+role);

        /*String logId = principal.getName(); // 로그인된 사용자의 아이디 가져오기*/

        MemberDTO memberDTO = memberService.loginByUsername(principal.getName());

        answerService.addAnswer(qna_no, answerForm.getAnswer(), memberDTO);


        return "guide/question/question_list";/*예시로 써둠*/
    }



}
