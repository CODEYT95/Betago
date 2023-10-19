package com.bnw.beta.domain.learning.dto;


import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class TaskDTO {
    //숙제 생성
    private int task_no;
    private String task_title;
    private String task_content;
    private String task_chapter;
    private Date task_regdate;
    private Date task_senddate;
    private Date task_deadline;
    private String member_id;
    private int tasksend_no;
}
