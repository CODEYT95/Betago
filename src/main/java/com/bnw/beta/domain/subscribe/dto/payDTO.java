package com.bnw.beta.domain.subscribe.dto;

import lombok.Data;

import java.util.Date;

@Data
public class payDTO {
    private int game_no;
    private int game_price;
    private int game_sell;
    private String game_title;
    private String game_date;


    private String pay_phone;
    private String member_name;
    private String member_id;
    private String pay_type;
    private String pay_name;
    private String buy_name;
    private Date pay_date;
    private Date pay_enddate;

    //파일
    private int filegame_no;
    private String filegame_name;
    private String filegame_path;
    private Date filegame_date;

    //매출
    private String day;
    private int total_sell;
    private int total_sales;
}
