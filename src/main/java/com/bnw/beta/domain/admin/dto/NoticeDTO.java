package com.bnw.beta.domain.admin.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class NoticeDTO {
    private Long notice_no;
    private String notice_title;
    private String notice_content;
    private String notice_category;
    private LocalDateTime notice_regdate;
    private String notice_isshow;
    private String member_id;
    private Long view_cnt;

    private List<NoticeFileDTO> noticeFiles;

    // getter 및 setter 추가
    public List<NoticeFileDTO> getNoticeFiles() {
        return noticeFiles;
    }

    public List<NoticeDTO> setNoticeFiles(List<NoticeFileDTO> noticeFiles) {
        this.noticeFiles = noticeFiles;
        return null;
    }
}
