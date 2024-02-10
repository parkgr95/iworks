package com.example.iworks.domain.file.dto.response;

import com.example.iworks.domain.file.domain.File;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class FileGetResponseDto {

    private int fileId;                     //파일 아이디
    private int fileCategoryCodeId;    //파일 카테고리 아이디
    private int fileOwnerId;                //파일 주체 아이디
    private int fileUploaderId;             //파일 업로더 아이디
    private String fileOriginalName;        //원본 파일명
    private String fileSaveName;            //저장 파일명
    private long fileSize;                  //파일 크기
    private LocalDateTime fileCreatedAt;    //파일 생성일

    public FileGetResponseDto(File file) {
        this.fileId = file.getFileId();
        this.fileCategoryCodeId = file.getFileCategoryCode().getCodeId();
        this.fileOwnerId = file.getFileOwnerId();
        this.fileUploaderId = file.getFileUploaderId();
        this.fileOriginalName = file.getFileOriginalName();
        this.fileSaveName = file.getFileSaveName();
        this.fileSize = file.getFileSize();
        this.fileCreatedAt = file.getFileCreatedAt();
    }

}
