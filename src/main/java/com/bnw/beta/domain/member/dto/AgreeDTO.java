package com.bnw.beta.domain.member.dto;

import lombok.Data;

//약관정보를 관리. 필수 체크 약관은 vaildation에서 유효성 검사를 할거라 포함시키지 않음.
@Data
public class AgreeDTO {
    private Long terms_record;
    private String terms_title;
    private String terms_content;
}
