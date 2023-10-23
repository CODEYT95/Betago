package com.bnw.beta.domain.learning.dto;


import lombok.Data;

import java.sql.Date;

@Data
public class TaskDTO {
    //숙제 생성
    private int task_no;
    private String task_title;
    private String task_content;
    private Date task_deadline;
    private String task_chapter;

    //숙제 전송
    private int tasksend_no;
    private Date task_senddate;
    private int group_no;
    private String task_state;
    private String game_title;

    //숙제 제출
    private int tasksubmit_no;
    private String tasksubmit_chapter;
    private String tasksubmit_content;
    private String tasksubmit_add;
    private Date tasksubmit_regdate;
    private String tasksubmit_eval;
    private String tasksubmit_comment;

    //회원
    private Integer member_no;
    private String member_id;
    private String member_name;

    //무한 스크롤
    private int LIMIT;
    private int OFFSET;
}
