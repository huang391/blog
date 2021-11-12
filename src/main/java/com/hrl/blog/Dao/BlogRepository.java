package com.hrl.blog.Dao;

import com.hrl.blog.entities.Blog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BlogRepository extends JpaRepository<Blog,Long>, JpaSpecificationExecutor {

    @Query("select b from Blog b where b.recommend=true")
    List<Blog> findTop(Pageable pageable);

    @Query("select b from Blog b where b.title like ?1 or b.content like ?1")
    Page<Blog> listByquery(String query, Pageable pageable);

    //更新views]
    @Modifying//更新数据库需要
    @Query("update Blog b  set b.views = b.views+1 where b.id = ?1")
    int updateviews(Long id);
}
