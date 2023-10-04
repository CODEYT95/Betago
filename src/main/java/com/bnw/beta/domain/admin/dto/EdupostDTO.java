package com.bnw.beta.domain.admin.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Data
public class EdupostDTO {
    private Long edupost_no;
    private String edupost_title;
    private String edupost_category;
    private String edupost_date;
    private String edupost_type;
    private String edupost_fileadd;
    private String edupost_content;
    private String edupost_service;
    private String member_id;
    private List<MultipartFile> files = new ArrayList<>();
    private List<Long> removeFileIds = new ArrayList<>();
}
