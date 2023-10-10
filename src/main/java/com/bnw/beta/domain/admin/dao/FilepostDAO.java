package com.bnw.beta.domain.admin.dao;

import com.bnw.beta.domain.admin.dto.FilepostDTO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
@Mapper
public interface FilepostDAO {
    //파일 업로드
    void saveAll(List<FilepostDTO> files);

    List<FilepostDTO> findByNo(Long edupost_no);

    List<FilepostDTO> findById(List<Long> filepost_nos);

    int deleteByNo(int file_no);

    FilepostDTO findByfileNo(Long filepost_no);

}
