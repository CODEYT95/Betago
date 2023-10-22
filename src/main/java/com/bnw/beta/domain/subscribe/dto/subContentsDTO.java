package com.bnw.beta.domain.subscribe.dto;

import lombok.Data;

import java.util.Date;

//나의 구독상품 조회
@Data
public class subContentsDTO {
    private int game_no;
    private int pay_no;
    private int group_no;
    private int game_price;
    private int game_sell;
    private Date pay_date;
    private String game_date;
    private String pay_type;
    private String member_id;
    private String group_name;
    private String game_title;
    private String game_level;
    private Date endDate;

    private int filegame_no;
    private String filegame_name;

    private int limit;
    private int offset;

}
