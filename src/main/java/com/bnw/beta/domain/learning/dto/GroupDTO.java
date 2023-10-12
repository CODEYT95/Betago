package com.bnw.beta.domain.learning.dto;

import lombok.Data;
import java.sql.Date;

@Data
public class GroupDTO {

    //게임
    private int game_no;
    private String game_title;
    private int game_total;
    private Date game_startdate;
    private Date game_enddate;

    //그룹
    private int group_no;
    private String group_name;
    private Date group_startdate;
    private Date group_enddate;
    private int group_cnt;
    private int group_nowcnt;
    private String member_id;

    //회원
    private String member_name;
    private int member_no;
    private String member_phone;
    private String member_email;
    private Date join_date;

    //
    private int LIMIT;
    private int OFFSET;

}
