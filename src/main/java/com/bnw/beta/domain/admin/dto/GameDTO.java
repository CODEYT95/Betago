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
    private String game_date;
    private int game_total;
    private int game_price;
    private int game_discount;
    private int game_sell;
    private String game_content;
    private String member_id;
    private int game_amount;
    private Date sale_date;
    private int total_sales;
}
