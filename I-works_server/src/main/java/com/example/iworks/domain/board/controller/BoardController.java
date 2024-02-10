package com.example.iworks.domain.board.controller;

import com.example.iworks.domain.board.dto.request.BoardCreateRequestDto;
import com.example.iworks.domain.board.dto.request.BoardSearchRequestDto;
import com.example.iworks.domain.board.dto.request.BoardUpdateRequestDto;
import com.example.iworks.domain.board.dto.response.BoardGetResponseDto;
import com.example.iworks.domain.board.service.BoardService;
import com.example.iworks.domain.file.dto.request.FileUploadRequestDto;
import com.example.iworks.domain.file.service.FileService;
import com.example.iworks.global.model.Response;
import com.example.iworks.global.util.FileUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/board")
public class BoardController {

    private final BoardService boardService;
    private final FileService fileService;
    private final FileUtils fileUtils;
    private final Response response;

    //게시글 등록
    @PostMapping("/")
    public ResponseEntity<?> createBoard(@RequestBody BoardCreateRequestDto boardCreateRequestDto) {
        boardService.createBoard(boardCreateRequestDto);
        return response.handleSuccess("게시글 등록 완료");
    }

    //게시판 등록(파일 업로드)
    @PostMapping("/upload")
    public ResponseEntity<?> createBoardWithFile(@RequestPart(value = "boardCreateRequestDto") BoardCreateRequestDto boardCreateRequestDto, @RequestPart(value = "files", required = false) List<MultipartFile> files) {
        boardService.createBoard(boardCreateRequestDto);

        BoardGetResponseDto findBoard = boardService.getBoardRecent();
        List<FileUploadRequestDto> uploadFiles = fileUtils.uploadFiles(findBoard, files);
        fileService.uploadFile(uploadFiles);
        return response.handleSuccess("게시글 등록 완료");
    }

    //게시글 수정
    @PutMapping("/update/{boardId}")
    public ResponseEntity<?> updateBoard(@PathVariable(name = "boardId") int boardId, @RequestBody BoardUpdateRequestDto boardUpdateRequestDto) {
        boardService.updateBoard(boardId, boardUpdateRequestDto);
        return response.handleSuccess("게시글 수정 완료");
    }

    //게시글 삭제
    @PutMapping("/delete/{boardId}")
    public ResponseEntity<?> deleteBoard(@PathVariable(name = "boardId") int boardId) {
        boardService.deleteBoard(boardId);
        return response.handleSuccess("게시글 삭제 완료");
    }

    //게시글 전체 조회
    @GetMapping("/")
    public ResponseEntity<?> getBoards() {
        return response.handleSuccess(boardService.getAll());
    }

    //게시글 세부 조회
    @GetMapping("/{boardId}")
    public ResponseEntity<?> getBoard(@PathVariable(name = "boardId") int boardId) {
        return response.handleSuccess(boardService.getBoard(boardId));
    }

    //카테고리 별 게시글 전체 조회
    @GetMapping("/byCategory")
    public ResponseEntity<?> getBoardsByCategory(
            @RequestParam(name = "boardCategoryCodeId") int boardCategoryCodeId,
            @RequestParam(name = "boardOwnerId") int boardOwnerId) {
        return response.handleSuccess(boardService.getAllByCategory(boardCategoryCodeId, boardOwnerId));
    }
    
    //카테고리 별 게시글 세부 조회
    @GetMapping("/byCategory/{boardId}")
    public ResponseEntity<?> getBoardByCategory(
            @PathVariable(name = "boardId") int boardId,
            @RequestParam(name = "boardCategoryCodeId") int boardCategoryCodeId,
            @RequestParam(name = "boardOwnerId") int boardOwnerId) {
        return response.handleSuccess(boardService.getByCategory(boardId, boardCategoryCodeId, boardOwnerId));
    }

    //작성한 게시글 전체 조회
    @GetMapping("/byCreator")
    public ResponseEntity<?> getBoardsByCreator(@RequestParam(name = "boardCreatorId") int boardCreatorId) {
        return response.handleSuccess(boardService.getAllByCreator(boardCreatorId));
    }

    //키워드 별 게시글 검색
    @GetMapping("/search")
    public ResponseEntity<?> getBoardsByKeyword(@RequestBody BoardSearchRequestDto keyword) {
        return response.handleSuccess(boardService.getAllByKeyword(keyword));
    }

    //통합 키워드 별 게시글 검색
    @GetMapping("/total-search")
    public ResponseEntity<?> getBoardsByKeywords(@RequestParam(name = "keywords") String keywords) {
        return response.handleSuccess(boardService.getAllByKeywords(keywords));
    }
    
    //북마크 등록/삭제
    @PostMapping("/bookmark/{boardId}")
    public ResponseEntity<?> updateBookmark(@PathVariable(name = "boardId") int boardId, @RequestParam(name = "userEid") String userEid) {
        boardService.updateBookmark(boardId, userEid);
        return response.handleSuccess("북마크 완료");
    }

}
