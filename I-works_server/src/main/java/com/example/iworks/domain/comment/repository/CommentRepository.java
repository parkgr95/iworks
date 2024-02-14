package com.example.iworks.domain.comment.repository;

import com.example.iworks.domain.comment.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Integer>, CommentSearchRepository {

}
