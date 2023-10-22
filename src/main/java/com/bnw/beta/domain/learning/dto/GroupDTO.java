package com.bnw.beta.domain.learning.dto;

import lombok.Data;
import java.sql.Date;

@Data
public class GroupDTO {

    //게임
    private int game_no;
    private String game_title;
    private String game_content;
    private String game_level;
    private int game_total;
    private Date game_startdate;
    private Date game_enddate;
    private String filegame_name;

    //그룹
    private  int gm_no;
    private int group_no;
    private String group_name;
    private String group_intro;
    private Date group_startdate;
    private Date group_enddate;
    private String approve_state;
    private int group_cnt;
    private int group_nowcnt;
    private String member_id;
    private String member_level;

    //회원
    private String member_name;
    private int member_no;
    private Date join_date;
    private String member_phone;
    private String member_email;
    private Date approve_date;
    private int member_count;

    //결제
    private int pay_no;
    private Date pay_date;
    private Date pay_enddate;

    //
    private int LIMIT;
    private int OFFSET;


    public String getMember_phone() {
        if (member_phone != null && member_phone.length() == 11) {
            return member_phone.substring(0, 3) + "-"
                    + member_phone.substring(3, 7) + "-"
                    + member_phone.substring(7, 11);
        }
        return member_phone;
    }
}
