package com.bnw.beta.service.admin.edupost;

import com.bnw.beta.domain.admin.dao.EdupostDAO;
import com.bnw.beta.domain.admin.dto.EdupostDTO;
import com.bnw.beta.domain.common.paging.EdupostPageDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EdupostServiceImpl implements EdupostService {
    private final EdupostDAO edupostDao;
   //학습자료 등록
    @Override
    @Transactional
    public Long eduinsert(EdupostDTO dto) throws Exception {
        edupostDao.eduinsert(dto);
        return dto.getEdupost_no();
    }
    //학습자료 목록보기
    @Override
    public EdupostPageDTO edulist(int pageNum, int size, String searchType, String searchType2, String keyword) throws Exception {
        if(pageNum <= 0) {
            pageNum = 1;
        }
        int offset = (pageNum-1) * size;
        List<EdupostDTO> edupostList = edupostDao.edulist(offset, size, searchType, searchType2, keyword);
        int listCount = edupostDao.count(searchType, keyword);

        EdupostPageDTO edupostPageDTO = new EdupostPageDTO(listCount, pageNum, size, edupostList);
        edupostPageDTO.setListCount(listCount);

        return edupostPageDTO;
    }

/*        @Override
    public EdupostPageDTO edulist(int pageNum, int size, String keyword) {
        if(pageNum <= 0) {
            pageNum = 1;
        }
        int offset = (pageNum-1) * size;
        List<EdupostDTO> edupostList = edupostDao.edulist(offset, size, keyword);
        int listCount = edupostDao.count2(keyword);

        EdupostPageDTO edupostPageDTO = new EdupostPageDTO(listCount, pageNum, size, edupostList);
        edupostPageDTO.setListCount(listCount);

        return edupostPageDTO;
    }*/

  /*      @Override
    public EdupostPageDTO edulistTwo(int pageNum, int size, String searchType, String searchType2, String keyword){
        if(pageNum <= 0) {
            pageNum = 1;
        }
        int offset = (pageNum-1) * size;
        List<EdupostDTO> edupostList = edupostDao.edulistTwo(offset, size, searchType, searchType2, keyword);
        int listCount = edupostDao.count(searchType, keyword);

        EdupostPageDTO edupostPageDTO = new EdupostPageDTO(listCount, pageNum, size, edupostList);
        edupostPageDTO.setListCount(listCount);

        return edupostPageDTO;
    }*/

    @Override
    public int count(String searchType, String keyword) {
        return edupostDao.count(searchType, keyword);
    }
    //학습자료 상세정보
    @Override
    public EdupostDTO findPostId(final Long edupost_no) {
        return edupostDao.findById(edupost_no);
    }

    @Override
    public Long update(final EdupostDTO dto) {
        edupostDao.update(dto);
        return dto.getEdupost_no();
    }
    @Override
    public Long deletePost(final Long edupost_no) {
        edupostDao.deleteById(edupost_no);
        return edupost_no;
    }

}
