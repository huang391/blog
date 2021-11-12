package com.hrl.blog.Service;

import com.hrl.blog.entities.Blog;
import com.hrl.blog.vo.BlogQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BlogService {

    Blog getBlog(Long id);

    Blog saveBlog(Blog blog);

    Page<Blog> listBlog(Pageable pageable, BlogQuery blog);

    Page<Blog> listBlog(Pageable pageable);

    Page<Blog> listTagBlog(Pageable pageable, Long id);

    List<Blog> listBlogTop(Integer size);

    Blog updateBlog(Long id,Blog blog);

    void deleteBlog(Long id);

    Page<Blog> listByquery(String query, Pageable pageable);

    Blog getblog(Long id);
}
