package com.bnw.beta.controller.guide;

import com.bnw.beta.config.vaildation.question.QuestionForm;
import com.bnw.beta.domain.guide.dto.QuestionDTO;
import com.bnw.beta.domain.member.dto.MemberDTO;
import com.bnw.beta.service.guide.question.QuestionServiceImpl;
import com.bnw.beta.service.member.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RequestMapping("/question")
@RequiredArgsConstructor
@Controller
public class QuestionController {


    private final QuestionServiceImpl questionService;

    private final MemberService memberService;

    /*질문글 상세조회*/
    @GetMapping("/detail/{id}")
    public  String detail(@PathVariable("id") Integer id, Model model
    ){
        //1 파라미터 받기
        //2 비즈니스로직수행
        QuestionDTO question = questionService.getQuestion(id);
        //3 Model
        model.addAttribute("question",question);
        //4 view
        return "guide/question/question_detail"; //templates폴더하위.html
    }

    //질문글 등록폼
     @PreAuthorize("isAuthenticated()")
     @GetMapping("/add")
     public String add(QuestionForm questionForm){
        return "guide/question/question_form";
     }




//질문글작성

@PreAuthorize("isAuthenticated()") //로그인인증->로그인이 필요한 기능
@PostMapping("/add")
public String questionAdd(@Valid QuestionForm questionForm, BindingResult bindingResult,
                          Principal principal){
    if(bindingResult.hasErrors()) {
        return "question_form";  //valid QuestionForm 유효성검사후 BindingResult에 저장 그리고 그 값이 오류가 있다면
    }
    MemberDTO memberDTO = memberService.getUser(principal.getName())
            .orElseThrow(() -> new UsernameNotFoundException("User not found"));//user정보를 가져오기

    //questionForm.getSubject():유효성 검사를 통과한 테이터 폼에서 subject필드값 가져오기
    //questionForm.getContent():유효성 검사를 통과한 테이터 폼에서 subject필드값 가져오기
    questionService.add(questionForm.getSubject(), questionForm.getContent(), memberDTO);
    //3.Model
    //4.View
    return "redirect:/question/list";//질문목록조회요청을 통한_ 질문목록페이지로 이동
}




    @GetMapping("/list")
    public String questionList(Model model,
                               @RequestParam(value="page",defaultValue="1") int page){
        List<QuestionDTO> questions= this.questionService.getQuestions(page-1);
        model.addAttribute("questions",questions);
        return "guide/question/question_list";//  templates폴더하위  question_list.html문서
    }

}
