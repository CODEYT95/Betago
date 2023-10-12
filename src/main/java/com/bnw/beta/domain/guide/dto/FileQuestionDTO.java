package com.bnw.beta.domain.guide.dto;

import lombok.Data;

import java.util.Date;

@Data
public class FileQuestionDTO {
    private int filequ_no;
    private int qna_no;
    private String filequ_name;
    private String filequ_rename;
    private String filequ_path;
    private Date filequ_date;
}
