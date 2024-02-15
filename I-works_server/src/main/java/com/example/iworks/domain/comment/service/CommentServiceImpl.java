package com.example.iworks.domain.comment.service;

import com.example.iworks.domain.comment.domain.Comment;
import com.example.iworks.domain.comment.dto.reqeuest.CommentCreateRequestDto;
import com.example.iworks.domain.comment.dto.reqeuest.CommentUpdateRequestDto;
import com.example.iworks.domain.comment.dto.response.CommentGetResponseDto;
import com.example.iworks.domain.comment.exception.CommentErrorCode;
import com.example.iworks.domain.comment.exception.CommentException;
import com.example.iworks.domain.comment.repository.CommentRepository;
import com.example.iworks.domain.user.exception.UserErrorCode;
import com.example.iworks.domain.user.exception.UserException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class CommentServiceImpl implements CommentService{

    private final CommentRepository commentRepository;

    private final PageRequest pageRequest = PageRequest.of(0, 10);

    @Transactional
    @Override
    public void createComment(int userId, CommentCreateRequestDto commentCreateRequestDto) {
        commentRepository.save(commentCreateRequestDto.toEntity(userId));
    }

    @Transactional
    @Override
    public void updateComment(int commentId, int userId, CommentUpdateRequestDto commentUpdateRequestDto) {
        Comment comment = findComment(commentId);
        if (comment.getCommentCreatorId() != userId) {
            throw new UserException(UserErrorCode.USER_IS_NOT_AUTHORIZATION);
        }
        comment.update(commentUpdateRequestDto);
    }

    @Transactional
    @Override
    public void deleteComment(int commentId, int userId) {
        Comment comment = findComment(commentId);
        if (comment.getCommentCreatorId() != userId) {
            throw new UserException(UserErrorCode.USER_IS_NOT_AUTHORIZATION);
        }
        comment.delete();
    }

    @Override
    public List<CommentGetResponseDto> getAllByBoard(int boardId) {
        return commentRepository.findAllByBoard(pageRequest, boardId)
                .stream()
                .map(CommentGetResponseDto::new)
                .toList();
    }

    private Comment findComment(int commentId) {
        return commentRepository.findById(commentId)
                .filter(comment -> !Boolean.TRUE.equals(comment.getCommentIsDeleted()))
                .orElseThrow(() -> new CommentException(CommentErrorCode.COMMMENT_NOT_EXIST));
    }

}
