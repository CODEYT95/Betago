package com.bnw.beta.domain.learning.dto;

import lombok.Data;

import java.util.Date;

@Data
public class TaskSendDTO {

    //전송된 숙제
    private int task_no;
    private int tasksend_no;
    private int member_no;
    private String task_content;
    private String task_chapter;
    private Date task_deadline;
    private String task_state;
    private String member_name;
    private String game_title;
}
