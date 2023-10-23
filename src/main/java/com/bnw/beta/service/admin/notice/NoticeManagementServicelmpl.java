package com.bnw.beta.service.admin.notice;

import com.bnw.beta.domain.admin.dao.NoticeDAO;
import com.bnw.beta.domain.admin.dao.NoticeManagementDAO;
import com.bnw.beta.domain.admin.dto.NoticeDTO;
import com.bnw.beta.domain.common.paging.NoticePage;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.jaxb.SpringDataJaxb;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class NoticeManagementServicelmpl implements NoticeManagementService{

    private final NoticeManagementDAO noticeManagementDAO;
    //공지게시판 리스트 조회
    @Override
    public NoticePage noticeManagementList(int pageNum, int size, String searchType, String keyword) {
        int offset = (pageNum - 1) * size;
        if (pageNum <= 0) {
            pageNum = 1;
        }

        List<NoticeDTO> allList = noticeManagementDAO.noticeManagementList(pageNum,size,searchType,keyword);
        List<NoticeDTO> allNoticeList = new ArrayList<>();
        allNoticeList.addAll(allList);

        int listCnt = noticeManagementDAO.noticelistCnt(searchType, keyword);
        NoticePage noticePage = new NoticePage(listCnt, pageNum, size, allNoticeList, searchType, keyword);
        noticePage.setListCnt(listCnt);
        noticePage.setAllNoticeList(allNoticeList);

        return noticePage;
    }

    //총 게시글 개수 확인
    @Override
    public int listCnt(String searchType, String keyword) {
        return this.noticeManagementDAO.noticelistCnt(searchType, keyword);
    }

    //공지게시판 삭제
    @Override
    public void delete(List<Long> notice_no) {
        noticeManagementDAO.managementDelete(notice_no);
    }
    //드롭다운 삭제
    @Override
    public void delete1(String notice_no) {
        noticeManagementDAO.managementDelete1(notice_no);
    }

}
