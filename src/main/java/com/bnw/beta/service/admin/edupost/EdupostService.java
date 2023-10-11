package com.bnw.beta.service.admin.edupost;


import com.bnw.beta.domain.admin.dto.EdupostDTO;

import java.util.List;

public interface EdupostService {

    Long eduinsert(EdupostDTO dto) throws Exception;

    public List<EdupostDTO> edulist(int pageNum, int size) throws Exception;

    public int count();
    EdupostDTO findPostId(final Long edupost_no);

    Long update(final EdupostDTO dto);

    public Long deletePost(final Long edupost_no);

}