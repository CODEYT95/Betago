package com.bnw.beta.service.admin.edupost;


import com.bnw.beta.domain.admin.dto.EdupostDTO;
import com.bnw.beta.domain.common.paging.EdupostPageDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface EdupostService {

    Long eduinsert(EdupostDTO dto) throws Exception;

    public EdupostPageDTO edulist(@Param("page") int page, @Param("size") int size, @Param("searchType") String searchType, @Param("keyword") String keyword) throws Exception;

    public int count(String searchType, String keyword);
    EdupostDTO findPostId(final Long edupost_no);

    Long update(final EdupostDTO dto);

    public Long deletePost(final Long edupost_no);

}