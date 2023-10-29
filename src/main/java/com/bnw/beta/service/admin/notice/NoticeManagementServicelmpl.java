package com.bnw.beta.service.admin.notice;

import com.bnw.beta.domain.admin.dao.NoticeDAO;
import com.bnw.beta.domain.admin.dao.NoticeManagementDAO;
import com.bnw.beta.domain.admin.dto.NoticeDTO;
import com.bnw.beta.domain.admin.dto.NoticeManagementDTO;
import com.bnw.beta.domain.common.paging.NoticePage;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.jaxb.SpringDataJaxb;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
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

        List<NoticeDTO> allList = noticeManagementDAO.noticeManagementList(offset,size,searchType,keyword);
        List<NoticeDTO> allNoticeList = new ArrayList<>();
        allNoticeList.addAll(allList);

        int listCnt = noticeManagementDAO.noticelistCnt(searchType, keyword);
        NoticePage noticePage = new NoticePage(listCnt, pageNum, size, allNoticeList, searchType, keyword);
        noticePage.setListCnt(listCnt);
        noticePage.setAllNoticeList(allNoticeList);

        System.out.println("목록"+noticePage.getAllNoticeList().toString());

        return noticePage;
    }

    //총 게시글 개수 확인
    @Override
    public int listCnt(String searchType, String keyword) {
        return this.noticeManagementDAO.noticelistCnt(searchType, keyword);
    }

    //공지게시판 삭제
    @Override
    public void delete(List<Integer> notice_no) {
        noticeManagementDAO.managementDelete(notice_no);
    }

    //드롭다운으로 N/Y변경
    public String updateStatus(String notice_isshow, Long notice_no) {
        System.out.println("서비스"+notice_isshow);
        System.out.println("서비스"+notice_no);

        if ("Y".equals(notice_isshow) || "N".equals(notice_isshow)) {
          int result =  noticeManagementDAO.updateStatus(notice_isshow,notice_no);
          if(result > 0){
              return "success";
          }else {
              return "fail";
          }
        }
        return "error";
    }

    //드롭다운으로 공지&FAQ 변경
    public String updateCategory(String notice_category, Long notice_no) {
        System.out.println("서비스"+notice_category);
        System.out.println("서비스"+notice_no);

        if ("공지".equals(notice_category) || "FAQ".equals(notice_category)) {
            int result =  noticeManagementDAO.updateCategory(notice_category,notice_no);
            if(result > 0){
                return "success";
            }else {
                return "fail";
            }
        }
        return "error";
    }
    //드롭다운으로 전체공개, 상단, 일반 변경
    public String updateType(String type, Long notice_no) {
        System.out.println("서비스"+type);
        System.out.println("서비스"+notice_no);

        if ("upView".equals(type) || "일반".equals(type) || "allView".equals(type)|| "upView,allView".equals(type)) {
            int result =  noticeManagementDAO.updateType(type,notice_no);
            if(result > 0){
                return "success";
            }else {
                return "fail";
            }
        }
        return "error";
    }
    //드롭다운으로 날짜 변경
    public String updateReservation(LocalDate notice_reservation, Long notice_no) {
        System.out.println("서비스"+notice_reservation);
        System.out.println("서비스"+notice_no);

            int result =  noticeManagementDAO.updateReservation(notice_reservation,notice_no);
            if(result > 0){
                return "success";
            }else {
                return "fail";
            }
        }
    }




