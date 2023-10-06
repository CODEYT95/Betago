package com.bnw.beta.service.admin.edupost;


import com.bnw.beta.domain.admin.dto.FilepostDTO;

import java.util.List;

public interface FileEduService {
    void saveFiles(final Long edupost_no, final List<FilepostDTO> files);

    public List<FilepostDTO> findByNo(final Long edupostNo);
    public List<FilepostDTO> findById(final List<Long> ids);

    String deleteFileByNos(int file_no);

    public FilepostDTO findByfileNo(final Long filepost_no);
}
