package com.bnw.beta.domain.subscribe.dto;

import lombok.Data;
    @Data
    public class CartDTO {
        private int cart_no;
        private int game_no;
        private int game_price;
        private String game_title;
        private String member_id;
}
