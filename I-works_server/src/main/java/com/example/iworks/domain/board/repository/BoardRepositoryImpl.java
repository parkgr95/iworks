package com.example.iworks.domain.board.repository;

import com.example.iworks.domain.board.domain.Board;
import com.example.iworks.domain.board.dto.request.SearchKeyword;
import com.example.iworks.domain.board.dto.response.ResponseBoard;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

import static com.example.iworks.domain.board.domain.QBoard.board;

@Repository
@RequiredArgsConstructor
public class BoardRepositoryImpl implements BoardSearchRepository {

    private final JPAQueryFactory queryFactory;
    private final EntityManager em;

    @Override
    public List<ResponseBoard> findAllByKeyword(Pageable pageable, SearchKeyword keyword) {
        return queryFactory
                .selectFrom(board)
                .where(eqBoardCreatorId(keyword.getBoardCreatorId()),
                        eqBoardTitle(keyword.getBoardTitle()),
                        eqBoardContent(keyword.getBoardContent()))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch()
                .stream()
                .map(ResponseBoard::new)
                .collect(Collectors.toList());
    }

    private BooleanExpression eqBoardCreatorId(int boardOwnerId) {
        return board.boardOwnerId.eq(boardOwnerId);
    }

    private BooleanExpression eqBoardTitle(String boardTitle) {
        if (!StringUtils.hasText(boardTitle)) {
            return null;
        }
        return board.boardTitle.eq(boardTitle);
    }

    private BooleanExpression eqBoardContent(String boardContent) {
        if (!StringUtils.hasText(boardContent)) {
            return null;
        }
        return board.boardContent.eq(boardContent);
    }

    //QueryDsl로 수정
    //시작
    @Override
    public List<ResponseBoard> findAllByBoardCreatedId(Pageable pageable, int boardOwnerId) {
        List<Board> boards = em.createQuery("select b from Board b where b.boardOwnerId = :boardOwnerId")
                .setParameter("boardOwnerId", boardOwnerId)
                .setFirstResult(pageable.getPageNumber())
                .setMaxResults(pageable.getPageSize())
                .getResultList();

        return boards.stream()
                .map(ResponseBoard::new)
                .collect(Collectors.toList());
    }

    @Override
    public List<ResponseBoard> findAllByBoardTitle(Pageable pageable, String boardTitle) {
        List<Board> boards = em.createQuery("select b from Board b where b.boardTitle like :boardTitle")
                .setParameter("boardTitle", "%" + boardTitle + "%")
                .setFirstResult(pageable.getPageNumber())
                .setMaxResults(pageable.getPageSize())
                .getResultList();

        return boards.stream()
                .map(ResponseBoard::new)
                .collect(Collectors.toList());
    }

    @Override
    public List<ResponseBoard> findAllByBoardContent(Pageable pageable, String boardContent) {
        List<Board> boards = em.createQuery("select b from Board b where b.boardContent like :boardContent")
                .setParameter("boardContent", "%" + boardContent + "%")
                .setFirstResult(pageable.getPageNumber())
                .setMaxResults(pageable.getPageSize())
                .getResultList();

        return boards.stream()
                .map(ResponseBoard::new)
                .collect(Collectors.toList());
    }

    @Override
    public List<ResponseBoard> findAllByBoardTitleOrBoardContent(Pageable pageable, String boardTitle, String boardContent) {
        List<Board> boards = em.createQuery("select b from Board b where b.boardTitle like :boardTitle or b.boardContent like :boardContent")
                .setParameter("boardTitle", "%" + boardTitle + "%")
                .setParameter("boardContent", "%" + boardContent + "%")
                .setFirstResult(pageable.getPageNumber())
                .setMaxResults(pageable.getPageSize())
                .getResultList();

        return boards.stream()
                .map(ResponseBoard::new)
                .collect(Collectors.toList());
    }
    //끝
}
