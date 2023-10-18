package com.bnw.beta.controller.member.login;

import com.bnw.beta.domain.admin.dto.GameDTO;
import com.bnw.beta.domain.admin.dto.NoticeDTO;
import com.bnw.beta.domain.common.paging.MemberPageDTO;
import com.bnw.beta.domain.member.dto.MemberDTO;
import com.bnw.beta.service.admin.game.GameService;
import com.bnw.beta.service.admin.notice.NoticeService;
import com.bnw.beta.service.member.MemberService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;


@Controller
public class MemberController {
    private final MemberService memberService;
    private final NoticeService noticeService;
    private final GameService gameService;  // 게임 서비스를 추가합니다.


    @Autowired
    public MemberController(MemberService memberService, NoticeService noticeService, GameService gameService) {
        this.memberService = memberService;
        this.noticeService = noticeService;
        this.gameService = gameService;
    }

    //시큐리티 통해서 로그인폼 보여주기
    @GetMapping("/login")
    public String login(Principal principal, HttpSession session){
        System.out.println("실패");
        return "member/login/login_form";
    }



    @GetMapping("/findId")
    public String findId(){
        return "/member/login/findId";
    }

    @GetMapping("/findPw")
    public String findPw(){
        return "/member/login/findPw";
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
        model.addAttribute("topNoticeList", topNoticeList);

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

    /*회원 목록조회*/
    @GetMapping("/member/list")
    public String memberlist(@RequestParam(value = "page", defaultValue = "1") int page,
                             @RequestParam(value = "size", defaultValue = "3") int size,
                             @RequestParam(value = "searchType", defaultValue = "") String searchType,
                             @RequestParam(value = "searchType2", defaultValue = "") String searchType2,
                             @RequestParam(value = "searchType3", defaultValue = "") String searchType3,
                             @RequestParam(value = "keyword", defaultValue="") String keyword, Model model) {

        MemberPageDTO memberPageDTO = memberService.memberlist(page, size, searchType, searchType2, searchType3, keyword);
        model.addAttribute("currentPage", memberPageDTO.getCurrentPage());
        model.addAttribute("listCount", memberPageDTO.getListCount());
        model.addAttribute("memberPageDTO", memberPageDTO);
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
