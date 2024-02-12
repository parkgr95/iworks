package com.example.iworks.domain.board.repository;

import com.example.iworks.domain.board.entity.Board;
import com.example.iworks.domain.board.dto.request.BoardSearchRequestDto;
import com.example.iworks.domain.board.repository.custom.BoardGetRepository;
import com.example.iworks.domain.board.repository.custom.BoardSearchRepository;
import com.example.iworks.domain.code.entity.Code;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.List;

import static com.example.iworks.domain.board.entity.QBoard.board;
import static com.example.iworks.domain.board.entity.QBookmark.bookmark;

@RequiredArgsConstructor
@Repository
public class BoardRepositoryImpl implements BoardGetRepository, BoardSearchRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<Board> findAllByCategory(Pageable pageable, Code boardCategoryCode, int boardOwnerId) {
        return queryFactory
                .selectFrom(board)
                .where(
                        eqBoardCategoryCode(boardCategoryCode)
                                .and(eqBoardOwnerId(boardOwnerId))
                                .and(eqDeleted())
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
    }

    @Override
    public Board findByCategory(int boardId, Code boardCategoryCode, int boardOwnerId) {
        return queryFactory
                .selectFrom(board)
                .where(
                        eqBoardCategoryCode(boardCategoryCode)
                                .and(eqBoardOwnerId(boardOwnerId))
                                .and(eqBoardId(boardId))
                                .and(eqDeleted())
                )
                .fetchOne();
    }

    @Override
    public List<Board> findAllByCreator(Pageable pageable, int boardCreatorId) {
        return queryFactory
                .selectFrom(board)
                .where(
                        eqBoardCreatorId(boardCreatorId)
                                .and(eqDeleted())
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
    }

    @Override
    public List<Board> findAllByKeyword(Pageable pageable, BoardSearchRequestDto keyword) {
        return queryFactory
                .selectFrom(board)
                .where(
                        eqBoardCreatorId(keyword.getBoardCreatorId())
                                .or(eqBoardTitle(keyword.getBoardTitle()))
                                .or(eqBoardContent(keyword.getBoardContent()))
                                .and(eqDeleted())
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
    }

    @Override
    public List<Board> findAllByKeywords(Pageable pageable, String keywords) {
        return queryFactory
                .selectFrom(board)
                .where(
                        eqBoardAndTitle(keywords, keywords)
                                .and(eqDeleted())
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
    }

    @Override
    public List<Board> findAllByBookmark(Pageable pageable, String userEid) {
        return queryFactory
                .selectFrom(board)
                .innerJoin(bookmark)
                .on(board.eq(bookmark.board))
                .where(
                        eqUser(userEid)
                                .and(eqDeleted())
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
    }

    private BooleanExpression eqBoardCategoryCode(Code boardCategoryCode) {
        return board.boardCategoryCode.eq(boardCategoryCode);
    }

    private BooleanExpression eqBoardOwnerId(int boardOwnerId) {
        return board.boardOwnerId.eq(boardOwnerId);
    }

    private Predicate eqBoardId(int boardId) {
        return board.boardId.eq(boardId);
    }

    private BooleanExpression eqBoardCreatorId(int boardCreatorId) {
        return board.boardCreatorId.eq(boardCreatorId);
    }

    private BooleanBuilder eqBoardAndTitle(String boardTitle, String boardContent) {
        return eqBoardTitle(boardTitle).or(eqBoardContent(boardContent));
    }

    private BooleanBuilder eqBoardTitle(String boardTitle) {
        return StringUtils.hasText(boardTitle) ? new BooleanBuilder(board.boardTitle.eq(boardTitle)) : new BooleanBuilder();
    }

    private BooleanBuilder eqBoardContent(String boardContent) {
        return StringUtils.hasText(boardContent) ? new BooleanBuilder(board.boardContent.eq(boardContent)) : new BooleanBuilder();
    }

    private BooleanExpression eqUser(String userEid) {
        return bookmark.user.userEid.eq(userEid);
    }

    private BooleanExpression eqDeleted() {
        return board.boardIsDeleted.isNull().or(board.boardIsDeleted.eq(Boolean.FALSE));
    }

}
