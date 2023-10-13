package com.bnw.beta.service.admin.notice;

import com.bnw.beta.domain.admin.dao.NoticeDAO;
import com.bnw.beta.domain.admin.dto.NoticeDTO;
import com.bnw.beta.domain.admin.dto.NoticeFileDTO;
import com.bnw.beta.domain.common.paging.NoticePage;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.*;

@Service
@AllArgsConstructor
public class NoticeServiceImpl implements NoticeService {
    private final NoticeDAO noticeDAO;

    //공지게시판 리스트 조회
    @Override
    public NoticePage noticeList(int pageNum, int size,String searchType, String keyword) {
        int offset = (pageNum-1) * size;
        if (pageNum <= 0) {
            pageNum = 1;
        }

        //상단고정게시물
        List<NoticeDTO> topNoticeList = noticeDAO.noticeTop();

        // 공지게시판 리스트 조회 - 상단고정 게시물과 일반 게시물을 병합
        List<NoticeDTO> allNoticeList = new ArrayList<>();
        allNoticeList.addAll(topNoticeList);

        // 일반 게시물 목록을 가져와서 allNoticeList에 추가
        List<NoticeDTO> noticeList = noticeDAO.noticeList(offset, size, searchType, keyword, topNoticeList);
        allNoticeList.addAll(noticeList);

        int listCnt = noticeDAO.listCnt(searchType,keyword);

        NoticePage noticePage = new NoticePage(listCnt,pageNum,size,allNoticeList, topNoticeList,searchType,keyword);
        noticePage.setListCnt(listCnt);
        return noticePage;
    }
    //상단 고정 게시물
    @Override
    public List<NoticeDTO> getTopNoticeList(){
        return noticeDAO.noticeTop();
    }

    //총 게시글 개수 확인
    @Override
    public int listCnt(String searchType, String keyword){
        return this.noticeDAO.listCnt(searchType,keyword);
    }

    //공지게시판 글 등록
    @Override
    public void insert(NoticeDTO noticeDTO, MultipartFile[][] file,
                       String type, Date timeWrite) throws IOException {

        System.out.println("dd"+timeWrite);
        noticeDTO.setType(type);
        if(timeWrite !=null){
            java.sql.Date TimeWrite = new java.sql.Date(timeWrite.getTime());
            noticeDTO.setNotice_reservation((TimeWrite));
        }
        boolean file1Empty = file[0][0] == null || file[0][0].isEmpty();
        boolean file2Empty = file[1][0] == null || file[1][0].isEmpty();
        if (file1Empty && file2Empty) {
            noticeDAO.insert(noticeDTO);
        }else {
            noticeDAO.insert(noticeDTO);
            String path = "C:/uploadfile/notice_img/";
            File directory = new File(path);
            if (!directory.exists()) {
                directory.mkdirs();
            }
            NoticeFileDTO noticeFileDTO = new NoticeFileDTO();
            for (MultipartFile[] fileList  : file) {
                for (MultipartFile nofile : fileList) {
                    if (nofile != null && !nofile.isEmpty()) {
                        String originName = nofile.getOriginalFilename();
                        System.out.println("서비스파일" + originName);
                        String extension = originName.substring(originName.lastIndexOf("."));
                        String reName = UUID.randomUUID().toString() + extension;
                        String savedPath = path + reName;
                        File savedFile = new File(path, reName);
                        nofile.transferTo(savedFile);
                        noticeFileDTO.setFile_name(originName);
                        noticeFileDTO.setFile_rename(reName);
                        noticeFileDTO.setFile_path(savedPath);
                        noticeFileDTO.setNotice_no(noticeDTO.getNotice_no());
                        noticeDAO.fileUpload(noticeFileDTO);
                    }
                }
            }
        }
    }

    //공지게시판 상세조회
    @Override
    public NoticeDTO detail(Long notice_no) {
        NoticeDTO noticeDTO = noticeDAO.detail(notice_no);
        List<NoticeFileDTO> fileList = noticeDAO.getNoticeFiles(notice_no);
        noticeDTO.setNoticeFiles(fileList);

        return noticeDTO;
    }

    //공지게시판 수정
    @Override
    public NoticeDTO update(Long notice_no, NoticeDTO noticeDTO, MultipartFile[] file) throws IOException {
        if (file != null && file.length > 0) {
            String path = "C:/uploadfile/notice_img/";
            File directory = new File(path);

            if (!directory.exists()) {
                directory.mkdirs();
            }
            //이전 파일 불러오기 & 삭제
            List<NoticeFileDTO> filed = noticeDAO.getNoticeFiles(notice_no);
            for (NoticeFileDTO savedfile : filed) {
                String filePath = savedfile.getFile_path();
                File fileDelete = new File(filePath);
                if (fileDelete.exists() && fileDelete.isFile()) {
                    fileDelete.delete();
                }
            }

            noticeDAO.deleteFile(notice_no);

            //파일 등록
            NoticeFileDTO noticeFileDTO = new NoticeFileDTO();
            for (MultipartFile nofile : file) {
                String originName = nofile.getOriginalFilename();
                String extension = originName.substring(originName.lastIndexOf("."));
                String reName = UUID.randomUUID().toString() + extension;
                String savedPath = path + reName;
                File savedFile = new File(path, reName);
                nofile.transferTo(savedFile);

                noticeFileDTO.setFile_name(originName);
                noticeFileDTO.setFile_rename(reName);
                noticeFileDTO.setFile_path(savedPath);
                noticeFileDTO.setNotice_no(noticeDTO.getNotice_no());
                noticeDAO.fileUpload(noticeFileDTO);
            }
        }
        noticeDAO.update(noticeDTO);
        return noticeDTO;
    }

    //공지게시판 삭제
    @Override
    public void delete(Long notice_no) {
        noticeDAO.delete(notice_no);
    }

    //조회수 증가 + 중복 방지
    @Override
    public void viewCnt(NoticeDTO noticeDTO) {
        noticeDAO.viewCnt(noticeDTO);
    }

}
