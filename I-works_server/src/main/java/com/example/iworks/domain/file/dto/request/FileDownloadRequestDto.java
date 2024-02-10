package com.example.iworks.domain.file.dto.request;

import lombok.Getter;

@Getter
public class FileDownloadRequestDto {

    private int fileId;
    private int fileCategoryCodeId;
    private int fileOwnerId;

}
