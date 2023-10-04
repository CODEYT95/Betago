package com.bnw.beta.domain.learning.dto;

import lombok.Data;
import java.sql.Date;

@Data
public class GroupDTO {

    private int game_no;
    private String game_title;
    private int game_total;
    private int group_no;
    private String group_name;
    private Date group_startdate;
    private Date group_enddate;
    private int group_cnt;
    private int group_nowcnt;
    private String member_id;
    private int LIMIT;
    private int OFFSET;

}
