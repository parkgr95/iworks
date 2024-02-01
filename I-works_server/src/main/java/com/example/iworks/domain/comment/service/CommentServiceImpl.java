package com.example.iworks.domain.comment.service;

import com.example.iworks.domain.comment.domain.Comment;
import com.example.iworks.domain.comment.dto.reqeuest.CommentCreateRequestDto;
import com.example.iworks.domain.comment.dto.reqeuest.CommentUpdateRequestDto;
import com.example.iworks.domain.comment.dto.response.CommentGetResponseDto;
import com.example.iworks.domain.comment.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class CommentServiceImpl implements CommentService{

    private final CommentRepository commentRepository;

    private final PageRequest pageRequest = PageRequest.of(0, 10);

    @Transactional
    @Override
    public void createComment(CommentCreateRequestDto commentCreateRequestDto) {
        commentRepository.save(commentCreateRequestDto.toEntity());
    }

    @Transactional
    @Override
    public void updateComment(int commentId, CommentUpdateRequestDto commentUpdateRequestDto) {
        Comment findComment = commentRepository.findById(commentId)
                .orElseThrow(IllegalStateException::new);
        findComment.update(commentUpdateRequestDto);
    }

    @Transactional
    @Override
    public void deleteComment(int commentId) {
        Comment findComment = commentRepository.findById(commentId)
                .orElseThrow(IllegalStateException::new);
        findComment.delete();
    }

    @Override
    public Page<CommentGetResponseDto> getAllByBoardId(int boardId) {
        return commentRepository.findAllByBoard(pageRequest, boardId);
    }

}
