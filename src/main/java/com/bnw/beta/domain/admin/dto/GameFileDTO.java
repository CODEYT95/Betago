package com.bnw.beta.domain.admin.dto;

import lombok.Data;

import java.util.Date;

@Data
public class GameFileDTO {

    private int filegame_no;
    private int game_no;
    private String filegame_name;
    private String filegame_path;
    private Date filegame_date;
}
