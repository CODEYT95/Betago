package com.bnw.beta.domain.learning.dto;

import lombok.Data;

import java.util.Date;

@Data
public class TaskSubmitDTO {
    private int task_no;
    private int tasksend_no;
    private int tasksubmit_no;
    private String tasksubmit_chapter;
    private String tasksubmit_content;
    private String member_id;
    private Date tasksubmit_regdate;
    private String tasksbumit_eval;
    private String task_state;
}
