package com.bnw.beta.service.admin.notice;

import com.bnw.beta.domain.admin.dao.NoticeDAO;
import com.bnw.beta.domain.admin.dto.NoticeDTO;
import com.bnw.beta.domain.admin.dto.NoticeFileDTO;
import com.bnw.beta.domain.common.paging.NoticePage;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Service
@AllArgsConstructor
public class NoticeServiceImpl implements NoticeService {
    private final NoticeDAO noticeDAO;

    //공지게시판 리스트 조회
    @Override
    public NoticePage noticeList(int pageNum, int size, String searchType, String keyword) {
        int offset = (pageNum - 1) * size;
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
        int listCnt = noticeDAO.listCnt(searchType, keyword);
        //NoticePage noticePage = new NoticePage(listCnt, pageNum, size, allNoticeList, topNoticeList, searchType, keyword);
        NoticePage noticePage = new NoticePage(listCnt, pageNum, size, allNoticeList, topNoticeList);
        noticePage.setListCnt(listCnt);
        noticePage.setAllNoticeList(noticeList);
        return noticePage;
    }

    //상단 고정 게시물
    @Override
    public List<NoticeDTO> getTopNoticeList() {
        return noticeDAO.noticeTop();
    }

    //총 게시글 개수 확인
    @Override
    public int listCnt(String searchType, String keyword) {
        return this.noticeDAO.listCnt(searchType, keyword);
    }

    //공지게시판 글 등록
    @Override
    public void insert(NoticeDTO noticeDTO, MultipartFile[] file,
                       String type, LocalDate timeWrite, HttpSession session) throws IOException {

        //세션에서 이름 불러오기
        String member_name = (String) session.getAttribute("member_name");
        noticeDTO.setMember_name(member_name);
        noticeDTO.setType(type);
        noticeDTO.setNotice_reservation(timeWrite);
        if (file[0].isEmpty()) {
            noticeDAO.insert(noticeDTO);
        } else {
            noticeDAO.insert(noticeDTO);
            String projectDir = System.getProperty("user.dir"); // 현재 프로젝트 디렉토리 가져오기
            Path uploadPath = Paths.get(projectDir, "src", "main", "resources", "static", "image", "notice");
            NoticeFileDTO noticeFileDTO = new NoticeFileDTO();
            for (MultipartFile nofile : file) {
                if (nofile != null && !nofile.isEmpty()) {
                    String originName = nofile.getOriginalFilename();
                    System.out.println("서비스파일" + originName);
                    String extension = originName.substring(originName.lastIndexOf("."));
                    String reName = UUID.randomUUID().toString() + extension;
                    String savedPath = uploadPath + reName;
                    File savedFile = new File(String.valueOf(uploadPath), reName);
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
    public int update(Long notice_no, NoticeDTO noticeDTO, MultipartFile[] file, String type, LocalDate timeWrite) throws IOException {
        String projectDir = System.getProperty("user.dir"); // 현재 프로젝트 디렉토리 가져오기
        Path uploadPath = Paths.get(projectDir, "src", "main", "resources", "static", "image", "notice");
        noticeDTO.setType(type);
        noticeDTO.setNotice_reservation(timeWrite);

        // 이전 파일 불러오기 & 삭제
        List<NoticeFileDTO> filed = noticeDAO.getNoticeFiles(notice_no);
        for (NoticeFileDTO savedfile : filed) {
            String filePath = savedfile.getFile_path();
            File fileDelete = new File(filePath);
            if (fileDelete.exists() && fileDelete.isFile()) {
                fileDelete.delete();
            }
        }
        noticeDAO.deleteFile(notice_no);
        for (MultipartFile nofile : file) {
            if (nofile != null && !nofile.isEmpty()) {
                String originName = nofile.getOriginalFilename();
                String extension = originName.substring(originName.lastIndexOf("."));
                String reName = UUID.randomUUID().toString() + extension;
                String savedPath = uploadPath + reName;
                File savedFile = new File(String.valueOf(uploadPath), reName);
                nofile.transferTo(savedFile);
                NoticeFileDTO noticeFileDTO = new NoticeFileDTO();
                noticeFileDTO.setFile_name(originName);
                noticeFileDTO.setFile_rename(reName);
                noticeFileDTO.setFile_path(savedPath);
                noticeFileDTO.setNotice_no(noticeDTO.getNotice_no());
                noticeDAO.fileUpload(noticeFileDTO);
            }
        }
        // 글 업데이트
        return noticeDAO.update(noticeDTO);
    }

    //공지게시판 삭제
    @Override
    public void delete(Long notice_no) {
        noticeDAO.delete(notice_no);
    }

    //조회수 증가
    @Override
    public void viewCnt(NoticeDTO noticeDTO) {
        noticeDAO.viewCnt(noticeDTO);
    }

}