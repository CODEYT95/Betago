package com.bnw.beta.domain.admin.dto;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class NoticeDTO {
    private Long notice_no;
    private String notice_title;
    private String notice_content;
    private String notice_category;
    private Date notice_regdate;
    private String notice_isshow;
    private String member_id;
    private Long view_cnt;
    private List<NoticeFileDTO> noticeFiles;

    //파일 컬럼 변수
    private int file_no;
    private String file_name;
    private String file_rename;
    private String file_path;
    private Date file_date;
}
