package com.bnw.beta.domain.admin.dto;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.sql.Date;
import java.util.List;

@Data
public class NoticeDTO {
    private Long notice_no;
    private String notice_title;
    private String notice_content;
    private String notice_category;
    private LocalDateTime notice_regdate;
    private Date notice_reservation = null; //예약글 시간
    private String notice_isshow;
    private String member_id;
    int view_cnt;
    private String type = " ";  //상단노출or전체공개or일반글

    private List<NoticeDTO> topNoticeList; //상단노출게시글리스트
    private List<NoticeFileDTO> noticeFiles;

}
