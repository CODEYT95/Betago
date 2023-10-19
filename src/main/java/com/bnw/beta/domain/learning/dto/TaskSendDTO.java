package com.bnw.beta.domain.learning.dto;

import lombok.Data;

import java.util.Date;

@Data
public class TaskSendDTO {

    //전송된 숙제
    private int task_no;
    private int tasksend_no;
    private int group_no;
    private Integer member_no;
    private String task_title;
    private String task_content;
    private String task_chapter;
    private Date task_deadline;
    private Date task_senddate;
    private String task_state;
    private String member_id;
    private String member_name;
    private String game_title;

    //무한 스크롤
    private int LIMIT;
    private int OFFSET;
}
