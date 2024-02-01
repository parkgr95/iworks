package com.example.iworks.domain.board.service;

import com.example.iworks.domain.board.domain.Board;
import com.example.iworks.domain.board.domain.Bookmark;
import com.example.iworks.domain.board.dto.request.BoardCreateRequestDto;
import com.example.iworks.domain.board.dto.request.BoardSearchRequestDto;
import com.example.iworks.domain.board.dto.request.BoardUpdateRequestDto;
import com.example.iworks.domain.board.dto.response.BoardGetResponseDto;
import com.example.iworks.domain.board.repository.BoardRepository;
import com.example.iworks.domain.board.repository.BookmarkRepository;
import com.example.iworks.domain.user.domain.User;
import com.example.iworks.domain.user.repository.UserRepository;
import com.example.iworks.global.model.entity.Code;
import com.example.iworks.global.model.repository.CodeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.util.stream.Collectors.toList;

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
        Code code = codeRepository.findById(boardCreateRequestDto.getBoardCategoryCodeId())
                .orElseThrow(IllegalStateException::new);
        boardRepository.save(boardCreateRequestDto.toEntity(code));
    }

    @Transactional
    public void updateBoard(int boardId, BoardUpdateRequestDto boardUpdateRequestDto) {
        Board findBoard = boardRepository.findById(boardId)
                .orElseThrow(IllegalStateException::new);
        findBoard.update(boardUpdateRequestDto);
    }

    @Transactional
    public void deleteBoard(int boardId) {
        Board findBoard = boardRepository.findById(boardId)
                .orElseThrow(IllegalStateException::new);
        findBoard.delete();
    }

    public List<BoardGetResponseDto> getAll() {
        return boardRepository.findAll(pageRequest)
                .stream()
                .filter(board -> !Boolean.TRUE.equals(board.getBoardIsDeleted()))
                .map(BoardGetResponseDto::new)
                .collect(toList());
    }

    @Override
    public BoardGetResponseDto getBoard(int boardId) {
        return boardRepository.findById(boardId)
                .filter(board -> !Boolean.TRUE.equals(board.getBoardIsDeleted()))
                .map(BoardGetResponseDto::new)
                .orElseThrow(IllegalStateException::new);
    }

    public List<BoardGetResponseDto> getAllByCategory(int boardCategoryCodeId, int boardOwnerId) {
        return boardRepository.findAllByCategory(pageRequest, findCode(boardCategoryCodeId), boardOwnerId);
    }

    @Override
    public BoardGetResponseDto getByCategory(int boardId, int boardCategoryCodeId, int boardOwnerId) {
        return boardRepository.findByCategory(boardId, findCode(boardCategoryCodeId), boardOwnerId);
    }

    @Override
    public List<BoardGetResponseDto> getAllByCreator(int boardCreatorId) {
        return boardRepository.findAllByCreator(pageRequest, boardCreatorId);
    }

    public List<BoardGetResponseDto> getAllByKeyword(BoardSearchRequestDto keyword) {
        return boardRepository.findAllByKeyword(pageRequest, keyword);
    }

    public List<BoardGetResponseDto> getAllByKeywords(String keywords) {
        return boardRepository.findAllByKeywords(pageRequest, keywords);
    }
    @Transactional
    public void updateBookmark(int boardId, String userEid) {
        Board findBoard = boardRepository.findById(boardId)
                .orElseThrow(IllegalStateException::new);
        System.out.println("findBoard = " + findBoard);
        User findUser = userRepository.findByUserEid(userEid);
        System.out.println("findUser = " + findUser.getUserEid());
        if (findBoard == null || findUser == null) {
            throw new IllegalStateException("해당 북마크가 없습니다.");
        }

        Bookmark findBookmark = bookmarkRepository.findBookmarkByBoardAndUser(findBoard, findUser);
        if (findBookmark == null) {
            Bookmark bookmark = Bookmark.builder()
                    .board(findBoard)
                    .user(findUser)
                    .bookmarkIsActive(true)
                    .build();
            bookmarkRepository.save(bookmark);
        } else {
            findBookmark.update();
        }
    }

    private Code findCode(int boardCategoryCodeId) {
        return codeRepository.findById(boardCategoryCodeId)
                .orElseThrow(IllegalStateException::new);
    }

}
