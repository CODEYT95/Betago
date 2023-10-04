package com.bnw.beta.controller.admin;

import com.bnw.beta.config.post.FileUtils;
import com.bnw.beta.domain.admin.dto.EdupostDTO;
import com.bnw.beta.domain.admin.dto.FilepostDTO;
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
            return "operate/edupost/eduboard";
        }
    }
    //학습자료 목록
    @GetMapping("/list")
    public String postList(Model model) throws Exception {
            List<EdupostDTO> list = edupostService.edulist();
            model.addAttribute("postList",list);
            return "operate/edupost/eduboardlist";
    }
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
        model.addAttribute("post", post);
        System.out.println("조회 값 : "+post);
        return "admin/edupost/eduboardupdate";
    }
    //학습자료 게시판 수정
    @PostMapping("/update/{edupost_no}")
    public String update(@PathVariable("edupost_no") Long edupost_no, @ModelAttribute EdupostDTO dto) {
        //게시글 수정
        try {
            // 1. 해당 게시물 조회
            EdupostDTO edupostDTO = edupostService.findPostId(edupost_no);

            edupostDTO.setEdupost_category(dto.getEdupost_category());
            edupostDTO.setEdupost_date(dto.getEdupost_date());
            edupostDTO.setEdupost_content(dto.getEdupost_content());
            edupostDTO.setEdupost_fileadd(dto.getEdupost_fileadd());
            edupostDTO.setEdupost_service(dto.getEdupost_service());
            edupostDTO.setEdupost_title(dto.getEdupost_title());
            edupostDTO.setEdupost_type(dto.getEdupost_type());
        edupostService.update(dto);
        List<FilepostDTO> uploadFiles = fileUtils.uploadFiles(dto.getFiles());
        // 파일 정보 저장
        fileEduService.saveFiles(dto.getEdupost_no(), uploadFiles);
        // 삭제할 파일 정보 조회
        List<FilepostDTO> deleteFiles = fileEduService.findById(dto.getRemoveFileIds());
        fileUtils.deleteFiles(deleteFiles);

        fileEduService.deleteFileByNos(dto.getRemoveFileIds());
        return "redirect:/edupost/list";

        }catch (Exception e) {
            logger.log(Level.SEVERE, "에러 메시지", e);
            return "admin/edupost/eduboardupdate";
        }
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
