package com.bnw.beta.domain.admin.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class FilepostDTO {
    private Long filepost_no; // 파일번호
    private Long edupost_no; // 게시글번호(FK)
    private String edupost_name; // 원본 파일명
    private String edupost_rename; // 저장 파일명
    private String edupost_path;
    private LocalDateTime edupost_date;
    private long edupost_size; //파일 크기


    @Builder
    public FilepostDTO(String edupost_name, String edupost_rename,
                       long edupost_size) {
        this.edupost_name = edupost_name;
        this.edupost_rename = edupost_rename;
        this.edupost_size = edupost_size;
    }
    public void setEdupost_No(Long edupost_no) {
        this.edupost_no=edupost_no;
    }

}