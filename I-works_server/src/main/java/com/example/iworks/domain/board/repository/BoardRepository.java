package com.example.iworks.domain.board.repository;

import com.example.iworks.domain.board.entity.Board;
import com.example.iworks.domain.board.repository.custom.BoardGetRepository;
import com.example.iworks.domain.board.repository.custom.BoardSearchRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board, Integer>, BoardGetRepository, BoardSearchRepository {

}
