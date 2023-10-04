package com.bnw.beta.domain.admin.dto;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Timestamp;
import java.util.Date;

@Data
public class GameDTO {

    private int game_no;
    private String game_title;
    private String game_level;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date game_startdate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date game_enddate;
    private int game_total;
    private int game_price;
    private int game_discount;
    private String game_sell;
    private String game_content;
    private String member_id;
    private int game_amount;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date game_startsearch;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date game_endsearch;
}
