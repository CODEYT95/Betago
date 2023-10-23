package com.bnw.beta.controller.admin;

import com.bnw.beta.config.post.FileUtils;
import com.bnw.beta.domain.admin.dto.EdupostDTO;
import com.bnw.beta.domain.admin.dto.FilepostDTO;
import com.bnw.beta.domain.common.paging.EdupostPageDTO;
import com.bnw.beta.domain.member.dto.MemberDTO;
import com.bnw.beta.service.admin.edupost.EdupostService;
import com.bnw.beta.service.admin.edupost.FileEduService;
import com.bnw.beta.service.member.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.nio.file.Paths;
import java.security.Principal;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@RequestMapping("/edupost")
@Controller
@RequiredArgsConstructor
public class EdupostController {
    private static final Logger logger = Logger.getLogger(EdupostController.class.getName());

    private final String uploadPath = Paths.get("C:", "develop", "upload-files").toString();
    private final MemberService memberService;
    private final EdupostService edupostService;
    private final FileEduService fileEduService;
    private final FileUtils fileUtils;
    //학습자료 등록
    @GetMapping("/insert")
    public String eduInsert(Model model) {
        model.addAttribute("edupostInsert",1);
        return "/admin/edupost/eduboard";
    }
    @PostMapping("/admin/insert")
    public String eduInsertin(@ModelAttribute("dto") EdupostDTO dto, Principal principal) {
        dto.setMember_id(principal.getName());
        try{
            Long edupost_no = edupostService.eduinsert(dto);
            System.out.println(edupost_no);
            List<FilepostDTO> files = fileUtils.uploadFiles(dto.getFiles());
            System.out.println(files);
            fileEduService.saveFiles(edupost_no, files);
            return "redirect:/edupost/list";
        }catch (Exception e) {
            logger.log(Level.SEVERE, "에러 메시지", e);
            System.out.println("db에 저장이 안되었습니다.");
            return "/admin/edupost/eduboard";
        }
    }
    //학습자료 목록
    @GetMapping("/list")
    public String postList(@RequestParam(value = "page", defaultValue = "1") int page,
                           @RequestParam(value = "size", defaultValue = "4") int size,
                           @RequestParam(value = "searchType", defaultValue = "") String searchType,
                           @RequestParam(value = "searchType2", defaultValue = "") String searchType2,
                           @RequestParam(value = "searchType3", defaultValue = "") String searchType3,
                           @RequestParam(value = "searchType4", defaultValue = "") String searchType4,
                           @RequestParam(value = "keyword", defaultValue = "") String keyword,
                           Model model) {

        EdupostPageDTO edupostList = edupostService.edulist(page, size, searchType, searchType2, searchType3, searchType4, keyword);

        model.addAttribute("edupostList",1);
        model.addAttribute("currentPage", edupostList.getCurrentPage());
        model.addAttribute("listCount", edupostList.getListCount());
        model.addAttribute("edupostPageDTO", edupostList);
        model.addAttribute("searchType", searchType);
        model.addAttribute("searchType2", searchType2);
        model.addAttribute("searchType3", searchType3);
        model.addAttribute("searchType4", searchType4);
        model.addAttribute("keyword", keyword);
            return "admin/edupost/eduboardlist";
    }
    //학습자료 세부내용
    @GetMapping("/detail/{edupost_no}")
    public String postView(@PathVariable("edupost_no") final Long edupost_no, Model model, Principal principal) {
        String username = principal.getName();
        System.out.println(username);
        MemberDTO member = memberService.getMemberInfo(username);
        EdupostDTO post = edupostService.findPostId(edupost_no);
        model.addAttribute("post", post);
        model.addAttribute("edupostList",1);
        System.out.println("조회 값 : "+post);
        if("유료".equals(member.getLicense())) {
            System.out.println("조회 값 : "+post);
            return "admin/edupost/eduboarddetail";
        }else if("무료".equals(member.getLicense()) && "무료".equals(post.getEdupost_service())) {
            return "admin/edupost/eduboarddetail";
        } else {
            return "error2";
        }
    }

    @GetMapping("/update/{edupost_no}")
    public String updateForm(@PathVariable("edupost_no") final Long edupost_no, Model model) {
        EdupostDTO post = edupostService.findPostId(edupost_no);
        model.addAttribute("fileList",fileEduService.findByNo(edupost_no));
        model.addAttribute("post", post);
        model.addAttribute("edupostInsert",1);
        return "admin/edupost/eduboardupdate";
    }
    //학습자료 게시판 수정
    @PostMapping("/update/{edupost_no}")
    public String update(@PathVariable("edupost_no") Long edupost_no, @ModelAttribute EdupostDTO dto, Model model) {
        //게시글 수정
        edupostService.update(dto);
        // 파일 업로드
        List<FilepostDTO> uploadFiles = fileUtils.uploadFiles(dto.getFiles());
        // 파일 정보 저장
        fileEduService.saveFiles(dto.getEdupost_no(), uploadFiles);

        return "redirect:/edupost/list";
    }
    // 게시글 삭제
    @PostMapping("/delete/{edupost_no}")
    public String deletePost(@PathVariable("edupost_no") final Long edupost_no) {
        edupostService.deletePost(edupost_no);
        return "redirect:/edupost/list";
    }
    //파일 삭제
    @PostMapping("filedelete")
    @ResponseBody
    public String fileDelete(@RequestParam(name = "file_no") int file_no){
        return fileEduService.deleteFileByNos(file_no);
    }

}
