package com.example.iworks.domain.admin.service.adminBoard;

import com.example.iworks.domain.board.dto.request.BoardUpdateRequestDto;
import com.example.iworks.domain.board.entity.Board;
import com.example.iworks.domain.board.exception.BoardErrorCode;
import com.example.iworks.domain.board.exception.BoardException;
import com.example.iworks.domain.board.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class AdminBoardServiceImpl implements AdminBoardService {

    private final BoardRepository boardRepository;

    @Transactional
    public void updateBoard(int boardId, int userId, BoardUpdateRequestDto boardUpdateRequestDto) {
        Board board = findBoard(boardId);
        board.update(userId, boardUpdateRequestDto);
    }

    @Transactional
    public void deleteBoard(int boardId, int userId) {
        Board board = findBoard(boardId);
        board.delete();
    }

    private Board findBoard(int boardId) {
        return boardRepository.findById(boardId)
                .filter(board -> !Boolean.TRUE.equals(board.getBoardIsDeleted()))
                .orElseThrow(() -> new BoardException(BoardErrorCode.BOARD_NOT_EXIST));
    }
}
