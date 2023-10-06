package com.bnw.beta.controller.admin;

import com.bnw.beta.config.post.FileUtils;
import com.bnw.beta.domain.admin.dto.FilepostDTO;
import com.bnw.beta.service.admin.edupost.FileEduService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.BufferUnderflowException;
import java.util.List;

@RequestMapping("/eduposts")
@RestController
@RequiredArgsConstructor
public class FilepostController {

    private final FileEduService fileEduService;
    private final FileUtils fileUtils;
    //파일리스트 조회
    @GetMapping("/{edupost_no}/files")
    public List<FilepostDTO> findByNo(@PathVariable("edupost_no") final Long edupost_no) {
        Logger logger = LoggerFactory.getLogger(getClass());
        logger.info("edupost_no: {}", edupost_no);

        return fileEduService.findByNo(edupost_no);
    }
    @GetMapping("{edupost_no}/files/{filepost_no}/download")
    public ResponseEntity<Resource> downloadFile(@PathVariable final Long edupost_no, @PathVariable final Long filepost_no) {
        FilepostDTO file = fileEduService.findByfileNo(filepost_no);
        Resource resource = fileUtils.readFileAsResource(file);
        try {
            String filename = URLEncoder.encode(file.getEdupost_name(), "UTF-8");
            String disposition = "attachment; filename=\"" + filename + "\"";
            System.out.println("경로" + filename);
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .header(HttpHeaders.CONTENT_LENGTH, file.getEdupost_size() + "")
                    .body(resource);

        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("filename encoding failed : " + file.getEdupost_name());

        }
    }

}
