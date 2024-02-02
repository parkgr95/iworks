package com.example.iworks.domain.board.repository;

import com.example.iworks.domain.board.domain.Board;
import com.example.iworks.domain.board.domain.Bookmark;
import com.example.iworks.domain.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookmarkRepository extends JpaRepository<Bookmark, Integer> {

    //게시글 별 북마크 여부 조회
    Bookmark findBookmarkByBoardAndUser(Board board, User user);

}
