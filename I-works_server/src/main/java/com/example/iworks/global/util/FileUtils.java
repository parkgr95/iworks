package com.example.iworks.global.util;

import com.example.iworks.domain.board.dto.response.BoardGetResponseDto;
import com.example.iworks.domain.file.dto.request.FileUploadRequestDto;
import com.example.iworks.domain.file.dto.response.FileGetResponseDto;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class FileUtils {

    private final String uploadPath = Paths.get("C:", "SSAFY", "upload-files").toString();

    /**
     * 다중 파일 업로드
     * @param getBoard - 게시글 정보
     * @param multipartFiles - 파일 객체 List
     * @return DB에 저장할 파일 정보 List
     */
    public List<FileUploadRequestDto> uploadFiles(BoardGetResponseDto getBoard, List<MultipartFile> multipartFiles) {
        List<FileUploadRequestDto> files = new ArrayList<>();
        for (MultipartFile multipartFile : multipartFiles) {
            if (multipartFile.isEmpty()) {
                continue;
            }
            files.add(uploadFile(getBoard, multipartFile));
        }
        return files;
    }

    /**
     * 단일 파일 업로드
     * @param getBoard - 게시글 정보
     * @param multipartFile - 파일 객체
     * @return DB에 저장할 파일 정보
     */
    public FileUploadRequestDto uploadFile(BoardGetResponseDto getBoard, MultipartFile multipartFile) {
        if (multipartFile.isEmpty()) {
            return null;
        }

        String fileOriginalName = multipartFile.getOriginalFilename();
        String fileSaveName = createFileSaveName(fileOriginalName);

        String today = LocalDate.now().format(DateTimeFormatter.ofPattern("yyMMdd"));
        String addedUploadPath = getUploadPath(today) + File.separator + fileSaveName;
        File fileUploadPath = new File(addedUploadPath);

        long fileSize = multipartFile.getSize();

        try {
            multipartFile.transferTo(fileUploadPath);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return FileUploadRequestDto.builder()
                .fileOwnerCategoryCodeId(getBoard.getBoardCategoryCodeId())
                .fileOwnerId(getBoard.getBoardId())
                .fileUploaderId(getBoard.getBoardCreatorId())
                .fileOriginalName(fileOriginalName)
                .fileSaveName(fileSaveName)
                .fileSize(fileSize)
                .build();
    }

    /**
     * 파일 삭제 (from Disk)
     * @param getFile - 추가 경로
     */
    public void deleteFile(FileGetResponseDto getFile) {
        String uploadedDate = getUploadedDate(getFile);
        String filePath = Paths.get(uploadPath, uploadedDate).toString();
        Path path = Paths.get(filePath);
        try {
            Files.delete(path);
        } catch (IOException e) {
            throw new RuntimeException("파일 삭제 중 오류가 발생했습니다.", e);
        }
    }

    /**
     * 다운로드할 첨부파일(리소스) 조회 (as Resource)
     * @param getFile - 첨부파일 상세정보
     * @return 첨부파일(리소스)
     */
    public Resource getFile(FileGetResponseDto getFile) {
        String uploadedDate = getUploadedDate(getFile);
        String filename = getFile.getFileSaveName();
        Path filePath = Paths.get(uploadPath, uploadedDate, filename);
        try {
            Resource resource = new UrlResource(filePath.toUri());
            if (!resource.exists() || !resource.isFile()) {
                throw new RuntimeException("file not found : " + filePath);
            }
            return resource;
        } catch (MalformedURLException e) {
            throw new RuntimeException("file not found : " + filePath);
        }
    }

    /**
     * 저장 파일명 생성
     * @param fileName 원본 파일명
     * @return 디스크에 저장할 파일명
     */
    private String createFileSaveName(String fileName) {
        String uuid = UUID.randomUUID().toString().replace("-", "");
        String extension = StringUtils.getFilenameExtension(fileName);
        return uuid + "." + extension;
    }

    /**
     * 업로드 경로 반환
     * @param addPath - 추가 경로
     * @return 업로드 경로
     */
    private String getUploadPath(String addPath) {
        return makeDirectories(uploadPath + File.separator + addPath);
    }

    /**
     * 업로드 일자 반환
     * @param getFile - 첨부파일 상세정보
     * @return 업로드 일자
     */
    private static String getUploadedDate(FileGetResponseDto getFile) {
        return getFile.getFileCreatedAt().format(DateTimeFormatter.ofPattern("yyMMdd"));
    }
    
    /**
     * 업로드 폴더(디렉터리) 생성
     * @param uploadPath - 업로드 경로
     * @return 업로드 경로
     */
    private String makeDirectories(String uploadPath) {
        File fileUploadPath = new File(uploadPath);
        if (!fileUploadPath.exists()) {
            fileUploadPath.mkdirs();
        }
        return fileUploadPath.getPath();
    }

}
