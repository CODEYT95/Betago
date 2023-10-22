package com.bnw.beta.controller.guide;


import com.bnw.beta.config.vaildation.question.AnswerForm;
import com.bnw.beta.domain.guide.dao.QuestionDAO;
import com.bnw.beta.domain.guide.dto.AnswerDTO;
import com.bnw.beta.domain.guide.dto.QuestionDTO;
import com.bnw.beta.domain.member.dto.MemberDTO;
import com.bnw.beta.service.guide.answer.AnswerService;
import com.bnw.beta.service.guide.question.QuestionService;
import com.bnw.beta.service.guide.question.QuestionServiceImpl;
import com.bnw.beta.service.member.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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

    /*@GetMapping("/delete/{ans_no}")
    public String answerDelete(@PathVariable("id") Integer id,
                               Principal principal) {

    }*/

    /*댓글삭제*/
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/delete/{ans_no}")
    public String deleteAnswer(@PathVariable("ans_no") int ans_no, Principal principal,
                               RedirectAttributes redirectAttributes, Model model) {
        // 현재 사용자 확인
        String username = principal.getName();
        model.addAttribute("qnaList",1);
        // 댓글 소유자 확인
        AnswerDTO answerDTO = answerService.findAnswerById(ans_no);
        if (answerDTO == null || !answerDTO.getMember_id().equals(principal.getName())) {
            // 댓글이 현재 사용자의 것이 아니면 오류 페이지로 리다이렉트
            return "error/notAuthorized";
        }

        int originalPostId = answerDTO.getQna_no();

        // 서비스를 통해 실제 댓글 삭제
        answerService.deleteAnswer(ans_no);
        // 댓글이 성공적으로 삭제되면, 목록 페이지 등 적절한 페이지로 리다이렉트
        return "redirect:/question/detail/" + originalPostId; // 적절한 경로로 변경 필요
    }


    @PreAuthorize("isAuthenticated()")
    @GetMapping("/modify/{ans_no}")
    public String getAnswerForm(@PathVariable("ans_no") int ans_no, AnswerForm answerForm,
                                Principal principal, Model model) {
        model.addAttribute("qnaList",1);
        AnswerDTO answerDTO = answerService.getAnswer(ans_no); // 혹은 적절한 메소드를 사용하여 ID로 댓글 가져오기
        if (answerDTO == null) {
            // 적절한 예외 처리 (데이터가 존재하지 않는 경우)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "댓글을 찾을 수 없습니다.");
        }

        if(!answerDTO.getMember_id().equals(principal.getName())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"수정권한이 없습니다.");
        }
        answerForm.setAnswer(answerForm.getAnswer());
        return "guide/answer/answer_form"; // 댓글 수정 페이지 뷰 이름 (실제 뷰 이름과 경로에 따라 다름)
    }

    /*수정처리*/
    @PostMapping("/modify/{ans_no}")
    public String modifyAnswer(@PathVariable("ans_no") int ans_no, @Valid AnswerForm answerForm,
                               BindingResult bindingResult, Principal principal) {
        System.out.println("수정 진입하는 답변 번호="+ans_no);
        if(bindingResult.hasErrors()){
            return "guide/answer/answer_form"; //answer_form.html문서로 이동
        }

        AnswerDTO answerDTO= answerService.getAnswer(ans_no);
        if (answerDTO == null) {
            // 적절한 예외 처리
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "댓글을 찾을 수 없습니다.");
        }

        if(!answerDTO.getMember_id().equals(principal.getName())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"수정권한이 없습니다.");
        }
        System.out.println("아이디 가져오는지 "+answerDTO.getMember_id().equals(principal.getName()));
        // 서비스를 통해 댓글 업데이트
        answerService.modifyAnswer(ans_no, answerForm.getAnswer());
        // 댓글이 속한 원 게시글의 상세 페이지로 리다이렉트 (URL은 실제 애플리케이션의 구조에 따라 달라집니다)
        return String.format("redirect:/question/detail/%d", answerDTO.getQna_no());
    }




    @PreAuthorize("isAuthenticated()")
    @PostMapping("/add/{qna_no}")
    public String addAnswer(@PathVariable("qna_no") int qna_no, Model model,
                            @Valid AnswerForm answerForm, BindingResult bindingResult,
                            Principal principal){
        System.out.println("댓글시작"+qna_no);

        if(bindingResult.hasErrors()){
            /*model.addAttribute("answerForm", answerForm);*/
            return "redirect:/question/list";
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

        return "redirect:/question/detail/"+qna_no;
       /* return "redirect:/question/list";*/
    }



}
