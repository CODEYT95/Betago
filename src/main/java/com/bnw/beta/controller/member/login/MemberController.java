package com.bnw.beta.controller.member.login;

import com.bnw.beta.config.vaildation.member.PasswordUtils;
import com.bnw.beta.domain.admin.dto.GameDTO;
import com.bnw.beta.domain.admin.dto.NoticeDTO;
import com.bnw.beta.domain.common.paging.MemberPageDTO;
import com.bnw.beta.domain.member.dao.MemberDAO;
import com.bnw.beta.domain.member.dto.MemberDTO;
import com.bnw.beta.service.admin.FAQ.FAQService;
import com.bnw.beta.service.admin.game.GameService;
import com.bnw.beta.service.admin.notice.NoticeService;
import com.bnw.beta.service.member.MailSendServiceImpl;
import com.bnw.beta.service.member.MemberService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Date;
import java.util.List;


@Controller
public class MemberController {
    private final MemberService memberService;
    private final NoticeService noticeService;
    private final GameService gameService;




    @Autowired
    private MailSendServiceImpl mailSendService;
    @Autowired
    private MemberDAO memberDAO;
    @Autowired
    private FAQService faqService;

    @Autowired
    public MemberController(MemberService memberService, NoticeService noticeService, GameService gameService,  MailSendServiceImpl mailSendService, MemberDAO memberDAO) {
        this.memberService = memberService;
        this.noticeService = noticeService;
        this.gameService = gameService;
        this.mailSendService = mailSendService;
        this.memberDAO = memberDAO;
    }

    //시큐리티 통해서 로그인폼 보여주기
    @GetMapping("/login")
    public String login(Principal principal, HttpSession session) {

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

    /*임시비밀번호 바꾸는 명령문*/
    @GetMapping("/resetPw")
    public String displayResetPasswordPage(@RequestParam("email") String email, Model model) {
        // 비밀번호 재설정 페이지를 표시하기 전에 이메일 주소가 유효한지 확인할 수도 있습니다.
        model.addAttribute("email", email);
        return "member/login/resetPassword"; // 비밀번호 재설정 HTML 페이지의 템플릿 이름.
    }

    @PostMapping("/resetPw")
    public String handlePasswordReset(@RequestParam(name ="email") String email,
                                      @ModelAttribute("currentPassword") String currentPassword, // 현재 비밀번호 필드 추가
                                      @ModelAttribute("password") String newPassword,
                                      @ModelAttribute("confirmPassword") String confirmPassword,
                                      Model model) {

        boolean isCurrentPasswordCorrect = memberService.checkPassword(email, currentPassword);
        if (!isCurrentPasswordCorrect) {
            model.addAttribute("error", "현재 비밀번호가 잘못되었습니다.");
            model.addAttribute("email", email);
            System.out.println("현재 비밀번호가 잘못되었습니다");
            return "member/login/resetPassword"; // 비밀번호 재설정 페이지로 다시 리다이렉트
        }

        // 여기서 새 비밀번호 유효성 검사 수행
         String validationResult = validateNewPassword(newPassword, confirmPassword);
        if (validationResult != null) {
            model.addAttribute("error", validationResult);
            model.addAttribute("email", email);
            System.out.println("유효성검사 실패");
            return "member/login/resetPassword"; // 비밀번호 재설정 페이지로 다시 리다이렉트

        }

        // 비밀번호 변경 로직
        try {
            memberService.changeUserPassword(email, newPassword);
            System.out.println("비밀번호 최종변경완료");
        } catch (Exception e) {
            model.addAttribute("error", "비밀번호 변경 중 문제가 발생했습니다.");
            model.addAttribute("email", email);
            System.out.println("비밀번호 변경중 오류발생");
            return "member/login/resetPassword";
        }

        // 변경이 성공적으로 완료되면, 로그인 페이지로 리디렉션하거나 성공 메시지를 표시합니다.
        return "redirect:/login";
    }

    private String validateNewPassword(String newPassword, String confirmPassword) {
        // 비밀번호 규칙 확인 (예: 길이, 문자, 숫자, 특수 문자 등)
        if (!newPassword.equals(confirmPassword)) {
            return "입력한 비밀번호가 서로 일치하지 않습니다.";
        }
        if (newPassword.length() < 8) {
            return "비밀번호는 최소 8자 이상이어야 합니다.";
        }
        if (!newPassword.matches(".*[a-zA-Z].*")) {
            return "비밀번호에는 적어도 하나의 알파벳이 포함되어야 합니다.";
        }
        if (!newPassword.matches(".*[0-9].*")) {
            return "비밀번호에는 적어도 하나의 숫자가 포함되어야 합니다.";
        }
        return null;
    }

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping({"","/"})
    public String index(Principal principal, HttpSession session, Model model) {

        NoticeDTO noticeDTO = new NoticeDTO();


        List<GameDTO> gameList = gameService.selectAll();
        gameList = gameList.subList(0, Math.min(gameList.size(), 6));
        model.addAttribute("gameList", gameList);

        List<NoticeDTO> topNoticeList = noticeService.getTopNoticeList();
        if (topNoticeList.size() > 5) {
            topNoticeList = topNoticeList.subList(0, 5);
        }
        model.addAttribute("topNoticeList", topNoticeList);

        List<NoticeDTO> faqList = faqService.faqAll();
        if (faqList.size() > 5) {
            faqList = faqList.subList(0, 5);
        }
        model.addAttribute("faqList", faqList);


        if (principal != null) {
            session.setAttribute("member_no", memberService.getMemberInfo(principal.getName()).getMember_no());
            session.setAttribute("member_name", memberService.getMemberInfo(principal.getName()).getMember_name());
            session.setMaxInactiveInterval(1800);
        }
        model.addAttribute("member_name", session.getAttribute("member_name"));
        return "main"; //메인페이지로 설정
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
    @ResponseBody
    public  String user() {
        return "user";
    }

    @GetMapping("/admin")
    public @ResponseBody String admin() {
        return "admin";
    }

    /*회원 목록조회*/
    @GetMapping("/member/list")
    public String memberlist(
            @RequestParam(name = "startDate", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
            @RequestParam(name = "endDate", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate,
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam(value = "searchType", defaultValue = "") String searchType,
            @RequestParam(value = "searchType2", defaultValue = "") String searchType2,
            @RequestParam(value = "searchType3", defaultValue = "") String searchType3,
            @RequestParam(value = "keyword", defaultValue="") String keyword, Model model) {
        System.out.println("1");
        MemberPageDTO memberPageDTO = memberService.memberlist(startDate, endDate, page, size, searchType, searchType2, searchType3, keyword);
        model.addAttribute("memberList",1);
        model.addAttribute("currentPage", memberPageDTO.getCurrentPage());
        model.addAttribute("listCount", memberPageDTO.getListCount());
        model.addAttribute("memberPageDTO", memberPageDTO);
        model.addAttribute("startDate",startDate);
        model.addAttribute("endDate",endDate);
        model.addAttribute("searchType", searchType);
        model.addAttribute("searchType2", searchType2);
        model.addAttribute("searchType3", searchType3);
        model.addAttribute("keyword", keyword);
        return "admin/edupost/memberlist";
    }
    /*회원 상세조회(관리자)*/
    @GetMapping("/detail/{member_id}")
    public String memberView(@PathVariable("member_id") String member_id, Model model) {
            MemberDTO member = memberService.getMemberInfo(member_id);
        if (member_id != null) {
            model.addAttribute("detail", member);
            return "admin/edupost/memberdetail";
        } else {
            return "member";
            // 멤버 정보가 없는 경우에 대한 처리 로직 추가
            // 예: 에러 페이지로 리다이렉트 또는 에러 메시지 표시 등
        }
    }
}
