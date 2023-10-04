package com.bnw.beta.domain.learning.dto;


import lombok.Data;

import java.util.Date;

@Data
public class TaskDTO {
    //숙제 생성
    private int task_no;
    private String task_title;
    private String task_content;
    private String task_chapter;
    private Date task_regdate;
    private Date task_deadline;
    private String member_id;

    //숙제 전송
    private int tasksend_no;
    private Date task_senddate;
    private int member_no;
    private int group_title;
    private String game_title;
    private String task_state;

    //숙제 제출
    private int tasksubmit_no;
    private String member_name;
    private String tasksubmit_chapter;
    private String tasksubmit_content;
    private Date tasksubmit_regdate;
    private String tasksbumit_eval;
    private String tasksubmit_comment;
}
