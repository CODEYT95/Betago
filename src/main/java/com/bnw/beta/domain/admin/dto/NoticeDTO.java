package com.bnw.beta.domain.admin.dto;

import lombok.Data;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class NoticeDTO {
    private Long notice_no;
    private String notice_title;
    private String notice_content;
    private String notice_category;
    private LocalDateTime notice_regdate;
    private LocalDate notice_reservation = null; //예약글 시간
    private String notice_isshow;
    private String member_name;
    int view_cnt;
    private String type = " ";  //상단노출or전체공개or일반글

    private List<NoticeDTO> topNoticeList; //상단노출게시글리스트
    private List<NoticeFileDTO> noticeFiles;

    private int faq_no;
    private String faq_title;
    private String faq_content;
    private String faq_isshow;

}
