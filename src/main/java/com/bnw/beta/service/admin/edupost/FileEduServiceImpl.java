package com.bnw.beta.service.admin.edupost;

import com.bnw.beta.domain.admin.dao.FilepostDAO;
import com.bnw.beta.domain.admin.dto.FilepostDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
@Service
@RequiredArgsConstructor
public class FileEduServiceImpl implements FileEduService {
    private final FilepostDAO filepostDao;
    //파일 등록
    @Override
    @Transactional
    public void saveFiles(final Long edupost_no, final List<FilepostDTO> files) {
        if (CollectionUtils.isEmpty(files)) {
            return;
        }
        for (FilepostDTO file : files) {
            file.setEdupost_No(edupost_no);
        }
        filepostDao.saveAll(files);
    }
    //파일 리스트 조회
    @Override
    public List<FilepostDTO> findByNo(final Long edupost_no) {
        return filepostDao.findByNo(edupost_no);
    }
    @Override
    public List<FilepostDTO> findById(final List<Long> ids) {
        if (CollectionUtils.isEmpty(ids)) {
            return Collections.emptyList();
        }
        return filepostDao.findById(ids);
    }
    @Override
    @Transactional
    public void deleteFileByNos(final List<Long> nos) {
        if(CollectionUtils.isEmpty(nos)) {
            return;
        }
        filepostDao.deleteByNo(nos);
    }


}
