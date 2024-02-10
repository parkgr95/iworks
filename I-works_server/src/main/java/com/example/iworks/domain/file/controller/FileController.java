package com.example.iworks.domain.file.controller;

import com.example.iworks.domain.file.dto.request.FileDownloadRequestDto;
import com.example.iworks.domain.file.dto.response.FileGetResponseDto;
import com.example.iworks.domain.file.service.FileService;
import com.example.iworks.global.model.Response;
import com.example.iworks.global.util.FileUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/file")
public class FileController {

    private final FileService fileService;
    private final FileUtils fileUtils;
    private final Response response;

    //파일 삭제
    @DeleteMapping("/{fileId}")
    public ResponseEntity<?> deleteFile(@PathVariable(name = "fileId") int fileId) {
        FileGetResponseDto findFiles = fileService.getFile(fileId);
        fileUtils.deleteFile(findFiles);

        fileService.deleteFile(fileId);
        return response.handleSuccess("파일 삭제 완료");
    }

    //파일 다운로드
    @GetMapping("/download/")
    public ResponseEntity<?> downloadFile(@RequestBody FileDownloadRequestDto fileDownloadRequestDto) {
        FileGetResponseDto findFile = fileService.getFileByOwner(fileDownloadRequestDto);
        Resource resource = fileUtils.getFile(findFile);
        try {
            String filename = URLEncoder.encode(findFile.getFileOriginalName(), "UTF-8");
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; fileName=\"" + filename + "\";")
                    .header(HttpHeaders.CONTENT_LENGTH, findFile.getFileSize() + "")
                    .body(resource);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("filename encoding failed : " + findFile.getFileOriginalName());
        }
    }

    //파일 전체 조회
    @GetMapping
    public ResponseEntity<?> getFiles() {
        return response.handleSuccess(fileService.getAll());
    }

    //파일 세부 조회
    @GetMapping("/{fileId}")
    public ResponseEntity<?> getFile(@PathVariable(name = "fileId") int fileId) {
        return response.handleSuccess(fileService.getFile(fileId));
    }

}
