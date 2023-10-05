package com.bnw.beta.domain.learning.dto;

import lombok.Data;

import java.util.Date;

@Data
public class TaskSubmitDTO {
    private int tasksend_no;
    private int tasksubmit_no;
    private String task_title;
    private String task_content;
    private Date task_deadline;
    private String task_chapter;
    private String tasksubmit_chapter;
    private String tasksubmit_content;
    private String tasksubmit_add;
    private int member_no;
    private String member_name;
    private String tasksbumit_eval;
    private String tasksubmit_comment;
    private String task_state;
    private String game_title;
}
