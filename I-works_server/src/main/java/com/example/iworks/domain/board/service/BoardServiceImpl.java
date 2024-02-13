package com.example.iworks.domain.board.service;

import com.example.iworks.domain.board.entity.Board;
import com.example.iworks.domain.board.entity.Bookmark;
import com.example.iworks.domain.board.dto.request.BoardCreateRequestDto;
import com.example.iworks.domain.board.dto.request.BoardSearchRequestDto;
import com.example.iworks.domain.board.dto.request.BoardUpdateRequestDto;
import com.example.iworks.domain.board.dto.response.BoardGetResponseDto;
import com.example.iworks.domain.board.repository.BoardRepository;
import com.example.iworks.domain.board.repository.BookmarkRepository;
import com.example.iworks.domain.user.domain.User;
import com.example.iworks.domain.user.repository.UserRepository;
import com.example.iworks.domain.code.entity.Code;
import com.example.iworks.domain.code.repository.CodeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class BoardServiceImpl implements BoardService{

    private final BoardRepository boardRepository;
    private final CodeRepository codeRepository;
    private final UserRepository userRepository;
    private final BookmarkRepository bookmarkRepository;

    private final PageRequest pageRequest = PageRequest.of(0, 10);

    @Transactional
    public void createBoard(BoardCreateRequestDto boardCreateRequestDto) {
        boardRepository.save(
                boardCreateRequestDto.toEntity(
                        findCode(boardCreateRequestDto.getBoardCategoryCodeId())
                )
        );
    }

    @Transactional
    public void updateBoard(int boardId, BoardUpdateRequestDto boardUpdateRequestDto) {
        Board findBoard = boardRepository.findById(boardId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "해당 게시글이 존재하지 않습니다."));
        findBoard.update(boardUpdateRequestDto);
    }

    @Transactional
    public void deleteBoard(int boardId) {
        Board findBoard = boardRepository.findById(boardId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "해당 게시글이 존재하지 않습니다."));
        findBoard.delete();
    }

    @Override
    public List<BoardGetResponseDto> getAll() {
        List<BoardGetResponseDto> findBoards = boardRepository.findAll(pageRequest)
                .stream()
                .filter(board -> !Boolean.TRUE.equals(board.getBoardIsDeleted()))
                .map(BoardGetResponseDto::new)
                .toList();
        if (findBoards.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "해당 게시글 목록이 존재하지 않습니다.");
        }
        return findBoards;
    }

    @Override
    public BoardGetResponseDto getBoard(int boardId) {
        return boardRepository.findById(boardId)
                .filter(board -> !Boolean.TRUE.equals(board.getBoardIsDeleted()))
                .map(BoardGetResponseDto::new)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "해당 게시글이 존재하지 않습니다."));
    }

    @Override
    public List<BoardGetResponseDto> getAllByCategory(int boardCategoryCodeId, int boardOwnerId) {
        List<BoardGetResponseDto> findBoards = boardRepository.findAllByCategory(pageRequest, findCode(boardCategoryCodeId), boardOwnerId)
                .stream()
                .map(BoardGetResponseDto::new)
                .toList();
        if (findBoards.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "해당 게시글 목록이 존재하지 않습니다.");
        }
        return findBoards;
    }

    @Override
    public BoardGetResponseDto getByCategory(int boardId, int boardCategoryCodeId, int boardOwnerId) {
        Board findBoard =  boardRepository.findByCategory(boardId, findCode(boardCategoryCodeId), boardOwnerId);
        if (findBoard == null) {
            throw new IllegalStateException("해당 게시글이 존재하지 않습니다.");
        }
        return new BoardGetResponseDto(findBoard);
    }

    @Override
    public List<BoardGetResponseDto> getAllByCreator(int boardCreatorId) {
        List<BoardGetResponseDto> findBoards = boardRepository.findAllByCreator(pageRequest, boardCreatorId)
                .stream()
                .map(BoardGetResponseDto::new)
                .toList();
        if (findBoards.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "해당 게시글 목록이 존재하지 않습니다.");
        }
        return findBoards;
    }

    @Override
    public List<BoardGetResponseDto> getAllByKeyword(BoardSearchRequestDto keyword) {
        List<BoardGetResponseDto> findBoards = boardRepository.findAllByKeyword(pageRequest, keyword)
                .stream()
                .map(BoardGetResponseDto::new)
                .toList();
        if (findBoards.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "해당 게시글 목록이 존재하지 않습니다.");
        }
        return findBoards;
    }

    @Override
    public List<BoardGetResponseDto> getAllByKeywords(String keywords) {
        List<BoardGetResponseDto> findBoards = boardRepository.findAllByKeywords(pageRequest, keywords)
                .stream()
                .map(BoardGetResponseDto::new)
                .toList();
        if (findBoards.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "해당 게시글 목록이 존재하지 않습니다.");
        }
        return findBoards;
    }

    @Transactional
    @Override
    public Boolean updateBookmark(int boardId, String userEid) {
        Board findBoard = boardRepository.findById(boardId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "해당 게시글이 존재하지 않습니다."));

        User findUser = userRepository.findByUserEid(userEid);
        if (findUser == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "해당 유저가 존재하지 않습니다.");
        }

        Bookmark findBookmark = bookmarkRepository.findBookmarkByBoardAndUser(findBoard, findUser);
        if (findBookmark == null) {
            Bookmark bookmark = Bookmark.builder()
                    .board(findBoard)
                    .user(findUser)
                    .bookmarkIsActive(true)
                    .build();
            bookmarkRepository.save(bookmark);
            return true;
        }
        else {
            findBookmark.update();
            return findBookmark.getBookmarkIsActive();
        }
    }

    @Override
    public List<BoardGetResponseDto> getAllByBookmark(String userEid) {
        List<BoardGetResponseDto> findBoards = boardRepository.findAllByBookmark(pageRequest, userEid)
                .stream()
                .map(BoardGetResponseDto::new)
                .toList();
        if (findBoards.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "해당 게시글 목록이 존재하지 않습니다.");
        }
        return findBoards;
    }

    private Code findCode(int boardCategoryCodeId) {
        return codeRepository.findById(boardCategoryCodeId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "해당 카테고리 값이 존재하지 않습니다."));
    }

}
