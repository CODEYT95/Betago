package com.bnw.beta.controller.guide;

import com.bnw.beta.config.vaildation.question.AnswerForm;
import com.bnw.beta.config.vaildation.question.QuestionForm;
import com.bnw.beta.domain.guide.dao.QuestionDAO;
import com.bnw.beta.domain.guide.dto.AnswerDTO;
import com.bnw.beta.domain.guide.dto.FileQuestionDTO;
import com.bnw.beta.domain.guide.dto.QuestionDTO;
import com.bnw.beta.domain.member.dto.MemberDTO;
import com.bnw.beta.service.guide.answer.AnswerService;
import com.bnw.beta.service.guide.question.QuestionService;
import com.bnw.beta.service.guide.question.QuestionServiceImpl;
import com.bnw.beta.service.member.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.File;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;
import java.util.List;

@RequestMapping("/question")
@RequiredArgsConstructor
@Controller
public class QuestionController {

    /*이미지 파일 서빙 미리보기*/
    @GetMapping("/image/{filequ_name}")
    public ResponseEntity<InputStreamResource> showImage(@PathVariable String filequ_name, Model model) {
        model.addAttribute("qnaList",1);
        try {
            String projectDir = System.getProperty("user.dir"); // 현재 프로젝트 디렉토리 가져오기
            Path uploadPath = Paths.get(projectDir, "src", "main", "resources", "static", "image", "guide", "question");
            Path imagePath = Paths.get(String.valueOf(uploadPath), filequ_name);
            InputStreamResource resource = new InputStreamResource(Files.newInputStream(imagePath));

            return ResponseEntity.ok()
                    .contentType(MediaType.IMAGE_JPEG) // or another appropriate type
                    .body(resource);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Image not found", e);
        }
    }

    @GetMapping("/download/{filequ_name}")
    public ResponseEntity<InputStreamResource> downloadImage(@PathVariable String filequ_name, Model model) {
        model.addAttribute("qnaList",1);
        try {
            String projectDir = System.getProperty("user.dir"); // 현재 프로젝트 디렉토리 가져오기
            Path uploadPath = Paths.get(projectDir, "src", "main", "resources", "static", "image", "guide", "question");
            Path imagePath = Paths.get(String.valueOf(uploadPath), filequ_name);
            InputStreamResource resource = new InputStreamResource(Files.newInputStream(imagePath));

            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .header("Content-Disposition", "attachment; filename=\"" + filequ_name + "\"")
                    .body(resource);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Image not found", e);
        }
    }



    @Autowired
    private QuestionDAO questionDAO;

    private final QuestionService questionService;
    private final AnswerService answerService;

    private final MemberService memberService;

    /*이미지 파일*/

    //질문 게시글 조회전 비밀번호 확인
    @PostMapping("/verifyPassword")
    public String verifyPassword(@RequestParam String pw, @RequestParam(name="id") Integer qna_no, Model model, AnswerForm answerForm,Principal principal) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        boolean isAdmin = auth.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"));
        model.addAttribute("isAdmin", isAdmin);
        AnswerDTO answerDTO= answerService.getAnswer(qna_no);   //답변 넘버 불러올까? 추가함 지워도 되는지 확인
        model.addAttribute("answerDTO", answerDTO); //답변 넘버 불러올까? 추가함 지워도 되는지 확인
        QuestionDTO questionDTO = questionService.selectQuestion(qna_no);
        System.out.println(qna_no);

        String loginId = (principal != null) ? principal.getName() : "Anonymous";
        //로그인유저 널값

        List<QuestionDTO> question = questionService.getQuestionInfo(qna_no);
        /*QuestionDTO question2 = questionService.getQuestion(id);*/
        System.out.println(question);
        model.addAttribute("qna_pw", questionDTO.getQna_pw());
        /*model.addAttribute("qna_pw", question.getQna_pw());*/
        if (question != null && questionDTO.getQna_pw().equals(pw)) {
            FileQuestionDTO fileQuestion = questionDAO.selectFilesByQnaNo(qna_no);
            if (fileQuestion != null) {
                model.addAttribute("fileQuestion", fileQuestion);
            }
            model.addAttribute("login_id", loginId); //로그인유저 널값
            model.addAttribute("login_id", principal.getName());
            model.addAttribute("qna_id", questionDTO.getMember_id());
            model.addAttribute("question", question);
            model.addAttribute("questionDTO", questionDTO);
            /*model.addAttribute("question2", question2);*/

            System.out.println(model.asMap());
            model.addAttribute("isPasswordCorrect", true);  // 비밀번호가 맞으면 true 값을 설정합니다.
            return "/guide/question/question_detail";
        } else {
            System.out.println("틀린 경우1");
            model.addAttribute("questionDTO", questionDTO);
            model.addAttribute("errormessage","비밀번호가 맞지 않습니다.");
            model.addAttribute("question", question);/*이거 안 넣어서 오류 났었음*/
            model.addAttribute("isPasswordCorrect", false); // 비밀번호가 틀리면 false 값을 설정합니다.
            return "/guide/question/question_detail";}
    }


    /*리다이렉션 할때 비밀번호를 보지 않는 상세조회*/
    @GetMapping("/detail/{qna_no}")
    public String detail(@PathVariable("qna_no") Integer qna_no, AnswerForm answerForm,
                         @RequestParam(value = "afterEdit", required = false, defaultValue = "false") boolean afterEdit,
                         Model model, Principal principal) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        boolean isAdmin = auth.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"));
        model.addAttribute("isAdmin", isAdmin);
        model.addAttribute("qnaList",1);
        AnswerDTO answerDTO= answerService.getAnswer(qna_no);   //답변 넘버 불러올까? 추가함 지워도 되는지 확인
        model.addAttribute("answerDTO", answerDTO); //답변 넘버 불러올까? 추가함 지워도 되는지 확인
        QuestionDTO questionDTO = questionService.selectQuestion(qna_no);

        if (principal != null) {
            String loginId = principal.getName();
            model.addAttribute("login_id", loginId);
        } else {
            // 만약 principal 객체가 null이면, 로그인하지 않은 사용자로 간주하고 적절한 처리를 수행할 수 있습니다.
            // 예를 들어, 로그인 페이지로 리다이렉트하거나 에러 메시지를 표시할 수 있습니다.
            return "redirect:/login"; // 로그인 페이지로 리다이렉트하는 예시입니다.
        }

        String loginId = (principal != null) ? principal.getName() : "Anonymous";

        List<QuestionDTO> question = questionService.getQuestionInfo(qna_no);
        /*QuestionDTO question2 = questionService.getQuestion(qna_no);*/
        FileQuestionDTO fileQuestion = questionDAO.selectFilesByQnaNo(qna_no);
        if (fileQuestion != null) {
            model.addAttribute("fileQuestion", fileQuestion);
        }

        model.addAttribute("login_id", loginId);
        model.addAttribute("login_id", principal.getName());
        model.addAttribute("qna_id", questionDTO.getMember_id());
        model.addAttribute("question", question);
        model.addAttribute("questionDTO", questionDTO);
        //3 Model
        /*model.addAttribute("question2", question2);*/

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
    public String questionModify(QuestionForm questionForm, AnswerForm answerForm,
                                 @PathVariable("qna_no") Integer qna_no,Principal principal, Model model){
        //1 파라미터 받기
        //2 비즈니스로직수행
        model.addAttribute("qnaList",1);
        QuestionDTO question = questionService.selectQuestion(qna_no); //질문상세
        if(!question.getMember_id().equals(principal.getName())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"수정권한이 없습니다.");
        }
        questionForm.setSubject(question.getQna_title());
        questionForm.setContent(question.getQna_content());
        //3 Model

        return "guide/question/question_form"; //질문등록폼으로 이동
    }


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
        questionService.modify(question, questionForm.getSubject(), questionForm.getContent(),questionForm.getPw(), questionForm.getFile());

        return String.format("redirect:/question/detail/%d?afterEdit=true", qna_no);
    }

    @GetMapping("/delete/{qna_no}")
    public String questionDelete(@PathVariable("qna_no") Integer qna_no,
                                 Principal principal, Model model){
        //1 파라미터 받기
        //2 비즈니스로직수행
        model.addAttribute("qnaList",1);
        QuestionDTO question = questionService.selectQuestion(qna_no);
        if(!question.getMember_id().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "삭제권한이 없습니다.");
        }
        questionService.deleteY(question);
        return "redirect:/question/list";
    }


    //질문글 등록폼
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/add")
    public String add(QuestionForm questionForm, Model model){
        model.addAttribute("qnaList",1);
        return "guide/question/question_form";
    }




//질문글작성

    @PreAuthorize("isAuthenticated()") //로그인인증->로그인이 필요한 기능
    @PostMapping("/add")
    public String questionAdd(@Valid QuestionForm questionForm, BindingResult bindingResult,
                              @RequestParam(value="file", required=false) MultipartFile file,
                              Principal principal){
        if(bindingResult.hasErrors()) {
            return "guide/question/question_form";  //valid QuestionForm 유효성검사후 BindingResult에 저장 그리고 그 값이 오류가 있다면
        }
        MemberDTO memberDTO = memberService.getUser(principal.getName())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));//user정보를 가져오기

        int qna_no=questionService.add(questionForm.getSubject(), questionForm.getContent(), questionForm.getPw(), file, memberDTO);

        return String.format("redirect:/question/detail/%d?afterEdit=true", qna_no);
        /*return "redirect:/question/list";*/
    }



    /*질문글 리스트 조회*/
    @GetMapping("/list")
     public String questionList(Model model, @RequestParam(value="page",defaultValue="1") int page){
         /*List<QuestionDTO> questions= this.questionService.getQuestions(page-1);
        model.addAttribute("questions",questions);*/
        int pageSize = 10;
        List<QuestionDTO> questionsWithAnswers = this.questionService.getQuestionsWithAnswerCount(page);

        int totalQuestions = this.questionService.getTotalQuestionsCount();
        int totalPages = (int) Math.ceil((double) totalQuestions / pageSize);

        model.addAttribute("questions", questionsWithAnswers);
        model.addAttribute("currentPage", page); // 현재 페이지 번호
        model.addAttribute("totalPages", totalPages); // 전체 페이지 수
        model.addAttribute("qnaList",1);
        return "guide/question/question_list";//  templates폴더하위  question_list.html문서
    }


    /*10.19꺼*/
    @GetMapping("/mypost")
    public String getMyQuestions(Model model, Principal principal,  @RequestParam(value="page",defaultValue="1") int page) {
        // 현재 사용자의 이름 가져오기
        if (principal == null) {
            return "redirect:/login";
        }
        String username = principal.getName();
        int pageSize = 10;
        /*List<QuestionDTO> questionsWithAnswers = this.questionService.getQuestionsWithAnswerCount(page);*/
        int totalQuestions = this.questionService.countQuestionsByUserId(username);
        int totalPages = (int) Math.ceil((double) totalQuestions / pageSize);

        List<QuestionDTO> myQuestions = this.questionService.findQuestionsByUserId(username, page, pageSize);
        /*model.addAttribute("questions", questionsWithAnswers);*/
        model.addAttribute("isMyPost", true);
        model.addAttribute("questions", myQuestions);
        model.addAttribute("currentPage", page); // 현재 페이지 번호
        model.addAttribute("totalPages", totalPages); // 전체 페이지 수
        model.addAttribute("qnaList",1);
        return "guide/question/question_list";//
    }



}
