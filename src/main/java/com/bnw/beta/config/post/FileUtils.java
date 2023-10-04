package com.bnw.beta.config.post;

import com.bnw.beta.domain.admin.dto.FilepostDTO;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class FileUtils  {
    private final String uploadPath = Paths.get("C:", "develop", "upload-files").toString();
/*
     * 다중 파일 업로드
     * @param multipartFiles - 파일 객체 List
     * @return DB에 저장할 파일 정보 List
*/
    public List<FilepostDTO> uploadFiles(final List<MultipartFile> multipartFiles) {
        List<FilepostDTO> files = new ArrayList<>();
        for (MultipartFile multipartFile : multipartFiles) {
            if (multipartFile.isEmpty()) {
                continue;
            }
            files.add(uploadFile(multipartFile));
        }
        return files;
    }
/*
*
     * 단일 파일 업로드
     * @param multipartFile - 파일 객체
     * @return DB에 저장할 파일 정보

*/
    public FilepostDTO uploadFile(final MultipartFile multipartFile) {

        if (multipartFile.isEmpty()) {
            return null;
        }

        String saveName = generateSaveFilename(multipartFile.getOriginalFilename());
        String today = LocalDate.now().format(DateTimeFormatter.ofPattern("yyMMdd"));
        String uploadPath = getUploadPath(today) + File.separator + saveName;


        File uploadFile = new File(uploadPath);

        try {
            multipartFile.transferTo(uploadFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return FilepostDTO.builder()
                .edupost_name(multipartFile.getOriginalFilename())
                .edupost_rename(saveName)
                .edupost_size(multipartFile.getSize())
                .build();
    }

/**
     * 저장 파일명 생성
     * @param filename 원본 파일명
     * @return 디스크에 저장할 파일명
     */

    private String generateSaveFilename(final String filename) {
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        String extension = StringUtils.getFilenameExtension(filename);
        return uuid + "." + extension;
    }

/**
     * 업로드 경로 반환
     * @return 업로드 경로
     */

    private String getUploadPath() {
        return makeDirectories(uploadPath);
    }

/*
*
     * 업로드 경로 반환
     * @param addPath - 추가 경로
     * @return 업로드 경로

*/

    private String getUploadPath(final String addPath) {
        return makeDirectories(uploadPath + File.separator + addPath);
    }
/*
*
     * 업로드 폴더(디렉터리) 생성
     * @param path - 업로드 경로
     * @return 업로드 경로

*/
    private String makeDirectories(final String path) {
        File dir = new File(path);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        return dir.getPath();
    }

    /**
     * 파일 삭제 (from Disk)
     * @param files - 삭제할 파일 정보 List
     */
    public void deleteFiles(final List<FilepostDTO> files) {
        if (CollectionUtils.isEmpty(files)) {
            return;
        }
        for (FilepostDTO file : files) {
            String uploadedDate = file.getEdupost_datetime().toLocalDate().format(DateTimeFormatter.ofPattern("yyMMdd"));
            deleteFile(uploadedDate, file.getEdupost_rename());
        }
    }
    /**
     * 파일 삭제 (from Disk)
     * @param addPath - 추가 경로
     * @param filename - 파일명
     */
    private void deleteFile(final String addPath, final String filename) {
        String filePath = Paths.get(uploadPath, addPath, filename).toString();
        deleteFile(filePath);
    }

    /**
     * 파일 삭제 (from Disk)
     * @param filePath - 파일 경로
     */
    private void deleteFile(final String filePath) {
        File file = new File(filePath);
        if (file.exists()) {
            file.delete();
        }
    }
/*    public Resource readFileAsResource(final FilepostDTO file) {
        String uploadedDate = file.getEdupost_datetime().toLocalDate().format(DateTimeFormatter.ofPattern("yyMMdd"));
        String filename = file.getSaveName();
        Path filePath = Paths.get(uploadPath, uploadedDate, filename);
        try {
            Resource resource = new UrlResource(filePath.toUri());
            if (resource.exists() == false || resource.isFile() == false) {
                throw new RuntimeException("file not found : " + filePath.toString());
            }
            return resource;
        } catch (MalformedURLException e) {
            throw new RuntimeException("file not found : " + filePath.toString());
        }
    }*/

}
