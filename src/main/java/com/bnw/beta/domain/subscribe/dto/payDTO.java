package com.bnw.beta.domain.subscribe.dto;

import lombok.Data;

@Data
public class payDTO {
    private int game_no;
    private int game_price;
    private String game_title;
    private String pay_phone;
    private String member_name;
    private String member_id;
    private String pay_type;
    private String pay_name;
    private String buy_name;
    private String pay_date;
}