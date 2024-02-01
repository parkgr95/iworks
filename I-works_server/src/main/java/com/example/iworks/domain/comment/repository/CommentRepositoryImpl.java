package com.example.iworks.domain.comment.repository;

import com.example.iworks.domain.comment.dto.response.CommentGetResponseDto;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.example.iworks.domain.board.domain.QBoard.board;
import static com.example.iworks.domain.comment.domain.QComment.comment;
import static java.util.stream.Collectors.*;


@RequiredArgsConstructor
@Repository
public class CommentRepositoryImpl implements CommentSearchRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<CommentGetResponseDto> findAllByBoard(Pageable pageable, int boardId) {
        List<CommentGetResponseDto> results = queryFactory
                .selectFrom(comment)
                .where(eqBoardId(boardId)
                        .and(eqDeleted()))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch()
                .stream()
                .map(CommentGetResponseDto::new)
                .collect(toList());

        JPAQuery<Long> countQuery = queryFactory
                .select(comment.count())
                .from(comment)
                .where(eqBoardId(boardId)
                        .and(eqDeleted()));

        return PageableExecutionUtils.getPage(results, pageable, countQuery::fetchOne);
    }

    private static BooleanExpression eqBoardId(int boardId) {
        return comment.boardId.eq(boardId);
    }

    private BooleanExpression eqDeleted() {
        return comment.commentIsDeleted.isNull().or(comment.commentIsDeleted.eq(Boolean.FALSE));
    }

}
