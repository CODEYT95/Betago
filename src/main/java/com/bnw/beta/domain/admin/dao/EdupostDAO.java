package com.bnw.beta.domain.admin.dao;

import com.bnw.beta.domain.admin.dto.EdupostDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface EdupostDAO {
    //게시글 등록
    void eduinsert(EdupostDTO dto) throws Exception;
    //게시글 리스트 조회
    List<EdupostDTO> edulist(@Param("page") int page, @Param("size") int size);

    int count();
    //게시글 조회
    EdupostDTO findById(Long edupost_no);
    //게시글 수정
    void update(EdupostDTO dto);

    //게시글 삭제
    void deleteById(Long edupost_no);
}
