package com.bnw.beta.controller.guide;

import com.bnw.beta.config.vaildation.question.QuestionForm;
import com.bnw.beta.domain.guide.dto.QuestionDTO;
import com.bnw.beta.domain.member.dto.MemberDTO;
import com.bnw.beta.service.guide.question.QuestionServiceImpl;
import com.bnw.beta.service.member.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;
import java.util.List;

@RequestMapping("/question")
@RequiredArgsConstructor
@Controller
public class QuestionController {


    private final QuestionServiceImpl questionService;

    private final MemberService memberService;

    //질문 게시글 조회전 비밀번호 확인
    @PostMapping("/question/verifyPassword")
    public String verifyPassword(@RequestParam String pw, @RequestParam(name="id") Integer id, Model model) {
        QuestionDTO question = questionService.selectQuestion(id);
        System.out.println(question);
        model.addAttribute(question.getQna_pw());
        if (question != null && question.getQna_pw().equals(pw)) {
            model.addAttribute("question", question);
            System.out.println(model.asMap());
            model.addAttribute("isPasswordCorrect", true);  // 비밀번호가 맞으면 true 값을 설정합니다.
            return "/guide/question/question_detail";
        } else {
            System.out.println("틀린 경우");
            model.addAttribute("errormessage","비밀번호가 맞지 않습니다.");
            model.addAttribute("question", question);/*이거 안 넣어서 오류 났었음*/
            model.addAttribute("isPasswordCorrect", false); // 비밀번호가 틀리면 false 값을 설정합니다.
            return "/guide/question/question_detail";}
    }

    /*질문글 상세조회*/
    /*기본 상세조회문
    @GetMapping("/detail/{qna_no}")
    public  String detail(@PathVariable("qna_no") Integer qna_no, Model model
    ){
        //1 파라미터 받기
        //2 비즈니스로직수행
        QuestionDTO question = questionService.selectQuestion(qna_no);
        model.addAttribute("qna_pw",question.getQna_pw());*//*생략가능한지 테스트*//*
        //3 Model
        model.addAttribute("question",question);
        model.addAttribute("isPasswordCorrect", false); // 초기 상태는 비밀번호가 틀린 상태로 설정
        //4 view
        return "guide/question/question_detail"; //templates폴더하위.html
    }
*/

    /*리다이렉션 할때 비밀번호를 보지 않는 상세조회*/
    @GetMapping("/detail/{qna_no}")
    public String detail(@PathVariable("qna_no") Integer qna_no,
                         @RequestParam(value = "afterEdit", required = false, defaultValue = "false") boolean afterEdit,
                         Model model) {


        //1 파라미터 받기
        //2 비즈니스로직수행
        QuestionDTO question = questionService.selectQuestion(qna_no);
        model.addAttribute("qna_pw", question.getQna_pw());
        //3 Model
        model.addAttribute("question", question);

        // afterEdit 파라미터가 true면 비밀번호 확인을 생략
        if (Boolean.TRUE.equals(afterEdit)) {
            model.addAttribute("isPasswordCorrect", true);
        } else {
            model.addAttribute("isPasswordCorrect", false); // 기본 상태는 비밀번호가 틀린 상태로 설정
        }

        //4 view
        return "guide/question/question_detail";
    }


    /*질문글 수정폼 보여주기 get*/
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/modify/{qna_no}")
    public String questionModify(QuestionForm questionForm,
                                 @PathVariable("qna_no") Integer qna_no,Principal principal){
        //1 파라미터 받기
        //2 비즈니스로직수행
        QuestionDTO question = questionService.selectQuestion(qna_no); //질문상세
        if(!question.getMember_id().equals(principal.getName())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"수정권한이 없습니다.");
        }
        questionForm.setSubject(question.getQna_title());
        questionForm.setContent(question.getQna_content());
        //3 Model

        return "guide/question/question_form"; //질문등록폼으로 이동
    }


    /*질문수정처리*/
    @PostMapping("/modify/{qna_no}")
    public String modify(@Valid QuestionForm questionForm, BindingResult bindingResult,
                         @PathVariable("qna_no") Integer qna_no, Principal principal){
        //1파라미터받기
        if(bindingResult.hasErrors()){
            return "guide/question/question_form";
        }
        //2비즈니스로직
        QuestionDTO question = questionService.selectQuestion(qna_no);
        if( !question.getMember_id().equals(principal.getName())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"수정권한이 없다");
        }

        questionService.modify(question, questionForm.getSubject(), questionForm.getContent(),question.getQna_pw());
        /*return String.format( "redirect:/question/detail",qna_no);*/
        return String.format("redirect:/question/detail/%d?afterEdit=true", qna_no);
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
            return "guide/question/question_form";  //valid QuestionForm 유효성검사후 BindingResult에 저장 그리고 그 값이 오류가 있다면
        }
        MemberDTO memberDTO = memberService.getUser(principal.getName())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));//user정보를 가져오기

        //questionForm.getSubject():유효성 검사를 통과한 테이터 폼에서 subject필드값 가져오기
        questionService.add(questionForm.getSubject(), questionForm.getContent(), questionForm.getPw(), memberDTO);
        //3.Model
        //4.View
        return "redirect:/question/list";//질문목록조회요청을 통한_ 질문목록페이지로 이동
    }



    /*질문글 리스트 조회*/
    @GetMapping("/list")
    public String questionList(Model model,
                               @RequestParam(value="page",defaultValue="1") int page){
        List<QuestionDTO> questions= this.questionService.getQuestions(page-1);
        model.addAttribute("questions",questions);
        return "guide/question/question_list";//  templates폴더하위  question_list.html문서
    }

}
