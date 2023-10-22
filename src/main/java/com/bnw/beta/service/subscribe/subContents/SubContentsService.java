package com.bnw.beta.service.subscribe.subContents;

import com.bnw.beta.domain.subscribe.dto.subContentsDTO;


import java.util.List;
//나의 구독상품 조회
public interface SubContentsService {
    List<subContentsDTO> selectContents(subContentsDTO subContentsDTO);

    int contentsCnt(subContentsDTO subContentsDTO);
    Integer deleteContents(int game_no);
}
