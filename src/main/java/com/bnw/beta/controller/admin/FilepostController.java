package com.bnw.beta.controller.admin;

import com.bnw.beta.config.post.FileUtils;
import com.bnw.beta.domain.admin.dto.FilepostDTO;
import com.bnw.beta.service.admin.edupost.FileEduService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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


}
