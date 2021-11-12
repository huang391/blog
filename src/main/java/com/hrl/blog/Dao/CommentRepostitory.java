package com.hrl.blog.Dao;

import com.hrl.blog.entities.Comment;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepostitory extends JpaRepository<Comment,Long> {

    List<Comment> findByBlogIdAndCommentParentNull(Long blogId, Sort sort);
}
