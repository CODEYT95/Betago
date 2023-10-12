package com.bnw.beta.controller.guide;


import com.bnw.beta.config.vaildation.question.AnswerForm;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequestMapping("/answer")
public class AnswerController {

    @PostMapping("/add/{qna_no}")
    public String addAnswer(@PathVariable("qna_no") int qna_no, Model model,
                            @Valid AnswerForm answerForm, BindingResult bindingResult,
                            Principal principal){
        System.out.println("댓글시작"+qna_no);


        return "guide/question/question_list";/*예시로 써둠*/
    }



}
