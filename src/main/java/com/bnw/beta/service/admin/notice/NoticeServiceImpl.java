package com.bnw.beta.service.admin.notice;

import com.bnw.beta.domain.admin.dao.NoticeDAO;
import com.bnw.beta.domain.admin.dto.NoticeDTO;
import com.bnw.beta.domain.admin.dto.NoticeFileDTO;
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
    public List<NoticeDTO> noticeList(NoticeDTO noticeDTO) {
        return noticeDAO.noticeList(noticeDTO);
    }

    //공지게시판 글 등록
    @Override
    public void insert(NoticeDTO noticeDTO, MultipartFile[] file, String member_id) throws IOException {
        System.out.println("글정보?" + noticeDTO);
        noticeDTO.setMember_id(member_id);

        if (file[0].isEmpty()) {
            noticeDAO.insert(noticeDTO);
        } else {
            noticeDAO.insert(noticeDTO);
            String path = "C:/uploadfile/notice_img/";
            File directory = new File(path);
            if (!directory.exists()) {
                directory.mkdirs();
            }

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
                noticeFileDTO.setFile_date(new Date());
                noticeFileDTO.setNotice_no(noticeDTO.getNotice_no());
                noticeDAO.fileUpload(noticeFileDTO);
            }
        }
    }
    //공지게시판 상세조회
    @Override
    public NoticeDTO detail(Long notice_no){
        NoticeDTO noticeDTO = noticeDAO.detail(notice_no);
        System.out.println("서비스DTO="+noticeDTO);
        return noticeDTO;
    }

    //공지게시판 수정
    @Override
    public void update(Long notice_no, NoticeDTO noticeDTO, MultipartFile[] newFile){
        noticeDTO = noticeDAO.detail(notice_no);
        noticeDAO.update(noticeDTO);


    }
}

