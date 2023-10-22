package com.bnw.beta.domain.admin.dto;
import lombok.Data;

import java.util.Date;

@Data
public class GameDTO {

    private int game_no;
    private String game_title;
    private String game_level;
    private String game_date;
    private int game_total;
    private int game_price;
    private int game_discount;
    private String game_sell;
    private String game_content;
    private String member_id;
    private int game_amount;
    private Date game_startsearch;
    private Date game_endsearch;
    private String game_isshow;
    //파일
    private int filegame_no;
    private String filegame_name;
    private String filegame_path;
    private Date filegame_date;
}
