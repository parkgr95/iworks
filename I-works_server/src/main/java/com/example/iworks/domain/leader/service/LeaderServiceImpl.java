package com.example.iworks.domain.leader.service;

import com.example.iworks.domain.board.entity.Board;
import com.example.iworks.domain.board.repository.BoardRepository;
import com.example.iworks.domain.user.repository.UserRepository;
import com.example.iworks.global.util.JwtProvider;
import com.example.iworks.global.util.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class LeaderServiceImpl implements LeaderService {

    private final BoardRepository boardRepository;

    private final Response response;

    private final JwtProvider jwtProvider;

    private final UserRepository userRepository;

    public ResponseEntity<Map<String, Object>> deleteBoard(int boardId,String token) {
        Board board = boardRepository.findById(boardId).get();

        int leaderDeptId = userRepository.findByUserId(jwtProvider.getUserId(token)).getUserDepartment().getDepartmentId();

        if (board == null) {
            return response.handleFail("존재하지 않는 게시물",null);
        }

        if(board.getBoardOwnerId() != leaderDeptId){
            return response.handleFail("권한이 없습니다.",null);
        }
        board.delete();
        return response.handleSuccess("게시물이 삭제되었습니다.");
    }
}
