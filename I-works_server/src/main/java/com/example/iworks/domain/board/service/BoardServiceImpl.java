package com.example.iworks.domain.board.service;

import com.example.iworks.domain.board.entity.Board;
import com.example.iworks.domain.board.entity.Bookmark;
import com.example.iworks.domain.board.dto.request.BoardCreateRequestDto;
import com.example.iworks.domain.board.dto.request.BoardSearchRequestDto;
import com.example.iworks.domain.board.dto.request.BoardUpdateRequestDto;
import com.example.iworks.domain.board.dto.response.BoardGetResponseDto;
import com.example.iworks.domain.board.exception.BoardErrorCode;
import com.example.iworks.domain.board.exception.BoardException;
import com.example.iworks.domain.board.repository.BoardRepository;
import com.example.iworks.domain.board.repository.BookmarkRepository;
import com.example.iworks.domain.code.exception.CodeErrorCode;
import com.example.iworks.domain.code.exception.CodeException;
import com.example.iworks.domain.user.domain.User;
import com.example.iworks.domain.user.exception.UserErrorCode;
import com.example.iworks.domain.user.exception.UserException;
import com.example.iworks.domain.user.repository.UserRepository;
import com.example.iworks.domain.code.entity.Code;
import com.example.iworks.domain.code.repository.CodeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class BoardServiceImpl implements BoardService{

    private final BoardRepository boardRepository;
    private final CodeRepository codeRepository;
    private final UserRepository userRepository;
    private final BookmarkRepository bookmarkRepository;
    private static final int BOARD_CATEGORY_ANNOUNCEMENT =1;
    private static final int BOARD_CATEGORY_FREE =2;
    private static final int BOARD_CATEGORY_DEPARTMENT =3;


    private final PageRequest pageRequest = PageRequest.of(0, 10);

    @Transactional
    public void createBoard(int userId, BoardCreateRequestDto boardCreateRequestDto) {
        User creator = userRepository.findByUserId(userId);

        boolean isAdmin = false;
        Code code = findCode(boardCreateRequestDto.getBoardCategoryCodeId());
        for (String role : creator.getRoleList()) {
            if (role.equals("ROLE_ADMIN")) {
                isAdmin = true;
                break;
            }
        }

        if(code.getCodeId()==BOARD_CATEGORY_ANNOUNCEMENT&& isAdmin){
            boardRepository.save(boardCreateRequestDto.toEntity(userId, code));
            return;
        }

        if(code.getCodeId()==BOARD_CATEGORY_FREE){
            boardRepository.save(boardCreateRequestDto.toEntity(userId, code));
            return;
        }

        if(code.getCodeId()==BOARD_CATEGORY_DEPARTMENT&& creator.getUserDepartment().getDepartmentId() == boardCreateRequestDto.getBoardOwnerId()){
            boardRepository.save(boardCreateRequestDto.toEntity(userId, code));
            return;
        }
        throw new UserException(UserErrorCode.USER_IS_NOT_AUTHORIZATION);
    }

    @Transactional
    public void updateBoard(int boardId, int userId, BoardUpdateRequestDto boardUpdateRequestDto) {
        Board board = findBoard(boardId);
        if (board.getBoardCreatorId() != userId) {
            throw new UserException(UserErrorCode.USER_IS_NOT_AUTHORIZATION);
        }
        board.update(userId, boardUpdateRequestDto);
    }

    @Transactional
    public void deleteBoard(int boardId, int userId) {
        Board board = findBoard(boardId);
        if (board.getBoardCreatorId() != userId) {
            throw new UserException(UserErrorCode.USER_IS_NOT_AUTHORIZATION);
        }
        board.delete();
    }

    @Override
    public List<BoardGetResponseDto> getAll() {
        return boardRepository.findAll(pageRequest)
                .stream()
                .filter(board -> !Boolean.TRUE.equals(board.getBoardIsDeleted()))
                .map(BoardGetResponseDto::new)
                .toList();
    }

    @Override
    public BoardGetResponseDto getBoard(int boardId) {
        return new BoardGetResponseDto(findBoard(boardId));
    }

    @Override
    public List<BoardGetResponseDto> getAllByCategory(int boardCategoryCodeId, int boardOwnerId) {
        return boardRepository.findAllByCategory(pageRequest, findCode(boardCategoryCodeId), boardOwnerId)
                .stream()
                .map(BoardGetResponseDto::new)
                .toList();
    }

    @Override
    public BoardGetResponseDto getByCategory(int boardId, int boardCategoryCodeId, int boardOwnerId) {
        Board findBoard =  boardRepository.findByCategory(boardId, findCode(boardCategoryCodeId), boardOwnerId);
        if (findBoard == null) {
            throw new BoardException(BoardErrorCode.BOARD_BY_CATEGORY_NOT_EXIST);
        }
        return new BoardGetResponseDto(findBoard);
    }

    @Override
    public List<BoardGetResponseDto> getAllByCreator(int boardCreatorId) {
         return boardRepository.findAllByCreator(pageRequest, boardCreatorId)
                .stream()
                .map(BoardGetResponseDto::new)
                .toList();

    }

    @Override
    public List<BoardGetResponseDto> getAllByKeyword(BoardSearchRequestDto keyword) {
        return boardRepository.findAllByKeyword(pageRequest, keyword)
                .stream()
                .map(BoardGetResponseDto::new)
                .toList();
    }

    @Override
    public List<BoardGetResponseDto> getAllByKeywords(String keywords) {
        return boardRepository.findAllByKeywords(pageRequest, keywords)
                .stream()
                .map(BoardGetResponseDto::new)
                .toList();
    }

    @Transactional
    @Override
    public Boolean activateBookmark(int boardId, int userId) {
        Board board = findBoard(boardId);
        User user = findUser(userId);

        Bookmark findBookmark = bookmarkRepository.findBookmarkByBoardAndUser(board, user);
        if (findBookmark == null) {
            Bookmark bookmark = createBookmark(board, user);
            bookmarkRepository.save(bookmark);
            return true;
        }
        else {
            findBookmark.update();
            return findBookmark.getBookmarkIsActive();
        }
    }

    @Override
    public List<BoardGetResponseDto> getAllByBookmark(int userid) {
        return boardRepository.findAllByBookmark(pageRequest, userid)
                .stream()
                .map(BoardGetResponseDto::new)
                .toList();
    }

    private Board findBoard(int boardId) {
        return boardRepository.findById(boardId)
                .filter(board -> !Boolean.TRUE.equals(board.getBoardIsDeleted()))
                .orElseThrow(() -> new BoardException(BoardErrorCode.BOARD_NOT_EXIST));
    }

    private Code findCode(int boardCategoryCodeId) {
        return codeRepository.findById(boardCategoryCodeId)
                .orElseThrow(() -> new CodeException(CodeErrorCode.CODE_NOT_EXIST));
    }

    private User findUser(int userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new UserException(UserErrorCode.USER_NOT_EXIST));
    }

    private static Bookmark createBookmark(Board board, User user) {
        return Bookmark.builder()
                .board(board)
                .user(user)
                .bookmarkIsActive(true)
                .build();
    }

}
