package com.bnw.beta.domain.admin.dto;

import lombok.Data;

import java.util.Date;

@Data
public class NoticeFileDTO {
    private Long file_no;
    private Long notice_no;
    private String file_name;
    private String file_rename;
    private String file_path;
    private Date file_date;
}
