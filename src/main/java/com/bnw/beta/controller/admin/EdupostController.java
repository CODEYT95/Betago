package com.bnw.beta.controller.admin;

import com.bnw.beta.config.post.FileUtils;
import com.bnw.beta.domain.admin.dto.EdupostDTO;
import com.bnw.beta.domain.admin.dto.FilepostDTO;
import com.bnw.beta.domain.common.paging.EdupostPageDTO;
import com.bnw.beta.service.admin.edupost.EdupostService;
import com.bnw.beta.service.admin.edupost.FileEduService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.nio.file.Paths;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@RequestMapping("/edupost")
@Controller
@RequiredArgsConstructor
public class EdupostController {
    private static final Logger logger = Logger.getLogger(EdupostController.class.getName());

    private final String uploadPath = Paths.get("C:", "develop", "upload-files").toString();
    private final EdupostService edupostService;
    private final FileEduService fileEduService;
    private final FileUtils fileUtils;
    //학습자료 등록
    @GetMapping("/insert")
    public String eduInsert() {
        return "admin/edupost/eduboard";
    }
    @PostMapping("/insert")
    public String eduInsertin(@ModelAttribute("dto") EdupostDTO dto) {
        System.out.println(dto);
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
                           @RequestParam(value = "keyword", defaultValue = "") String keyword,
                           Model model) throws Exception {

        EdupostPageDTO edupostList = edupostService.edulist(page, size, searchType, searchType2, keyword);

        model.addAttribute("currentPage", edupostList.getCurrentPage());
        model.addAttribute("listCount", edupostList.getListCount());
        model.addAttribute("edupostPageDTO", edupostList);
        model.addAttribute("searchType", searchType);
        model.addAttribute("searchType2", searchType2);
        model.addAttribute("keyword", keyword);
            return "admin/edupost/eduboardlist";
    }

  /*  @GetMapping("/list")
    public String postList(@RequestParam(value = "page", defaultValue = "1") int page,
                           @RequestParam(value = "size", defaultValue = "4") int size,
                           @RequestParam(value = "searchType", defaultValue = "") String searchType,
                           @RequestParam(value = "searchType2", defaultValue = "") String searchType2,
                           @RequestParam(value = "keyword", defaultValue = "") String keyword,
                           Model model) throws Exception {

        EdupostPageDTO edupostList;

        if (searchType.equals("all") && searchType2.equals("all")) {
            // 모든 검색 유형을 고려하지 않는 경우
            edupostList = edupostService.edulist(page, size, keyword);
        } else if (!searchType.equals("") && !searchType2.equals("")) {
            // 두 가지 검색 유형을 모두 고려하는 경우
            edupostList = edupostService.edulistTwo(page, size, searchType, searchType2, keyword);
        } else {
            // 하나의 검색 유형만 고려하는 경우
            edupostList = edupostService.edulist(page, size, searchType.equals("") ? searchType2 : searchType2 , keyword);
        }

        model.addAttribute("currentPage", edupostList.getCurrentPage());
        model.addAttribute("listCount", edupostList.getListCount());
        model.addAttribute("edupostPageDTO", edupostList );
        model.addAttribute("searchType ", searchType );
        model.addAttribute("searchType2 ", searchType2 );
        model.addAttribute("keyword" , keyword );

        return "admin/edupost/eduboardlist ";
    }*/
    //학습자료 세부내용
    @GetMapping("/detail/{edupost_no}")
    public String postView(@PathVariable("edupost_no") final Long edupost_no, Model model) {
        EdupostDTO post = edupostService.findPostId(edupost_no);
        model.addAttribute("post", post);
        System.out.println("조회 값 : "+post);
        return "admin/edupost/eduboarddetail";
    }
    @GetMapping("/update/{edupost_no}")
    public String updateForm(@PathVariable("edupost_no") final Long edupost_no, Model model) {
        EdupostDTO post = edupostService.findPostId(edupost_no);
        model.addAttribute("fileList",fileEduService.findByNo(edupost_no));
        System.out.println("파일번호리스트 : "+fileEduService.findByNo(edupost_no));
        model.addAttribute("post", post);
        System.out.println("조회 값 : "+post);
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
        //파일 업로드
 /*   @GetMapping("/download/{filepost_no}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String filename) {
        try {
            // 파일 경로 생성
            String filePath = uploadPath + File.separator + filename;

            // 파일 로드
            Resource resource = new UrlResource(Path.of(filePath).toUri());

            // Content-Type 추출
            String contentType = determineContentType(filename);

            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(contentType))
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                    .body(resource);
        } catch (Exception e) {
            return ResponseEntity.badRequest().contentLength(0).body(null);
        }
    }

    private String determineContentType(String filename) {
        // MIME 유형 결정 로직 추가 (예: 확장자에 따라 유형 지정)
        return "application/octet-stream";
    }*/

}
