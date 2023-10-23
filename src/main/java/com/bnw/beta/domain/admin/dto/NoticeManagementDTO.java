package com.bnw.beta.domain.admin.dto;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class NoticeManagementDTO {
    private List<Integer> notice_no;
    private String notice_title;
    private String notice_content;
    private String notice_category;
    private String notice_isshow;
    private LocalDateTime notice_regdate;
    private LocalDate notice_reservation;
    private String type = " ";
    private List<NoticeDTO> topNoticeList;
    private List<NoticeFileDTO> noticeFiles;

    private String newStatus;
    private Long noticeNo;


}
