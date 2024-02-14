package com.example.iworks.domain.comment.repository;

import com.example.iworks.domain.comment.domain.Comment;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.example.iworks.domain.comment.domain.QComment.comment;
import static java.util.stream.Collectors.*;

@RequiredArgsConstructor
@Repository
public class CommentRepositoryImpl implements CommentSearchRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<Comment> findAllByBoard(Pageable pageable, int boardId) {
        return queryFactory
                .selectFrom(comment)
                .where(
                        eqBoardId(boardId)
                                .and(eqDeleted())
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
    }

    private static BooleanExpression eqBoardId(int boardId) {
        return comment.boardId.eq(boardId);
    }

    private BooleanExpression eqDeleted() {
        return comment.commentIsDeleted.isNull().or(comment.commentIsDeleted.eq(Boolean.FALSE));
    }

}
