package com.hrl.blog.Service;

import com.hrl.blog.Dao.BlogRepository;
import com.hrl.blog.MException.notFoundException;
import com.hrl.blog.Util.MarkdownUtils;
import com.hrl.blog.Util.MyBeanUtils;
import com.hrl.blog.entities.Blog;
import com.hrl.blog.entities.Type;
import com.hrl.blog.vo.BlogQuery;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class BlogServiceImpl implements BlogService {

    @Autowired
    private BlogRepository blogRepository;

    @Override
    public Blog getBlog(Long id) {
        return blogRepository.getById(id);
    }

    @Transactional
    @Override
    public Blog saveBlog(Blog blog) {
        if(null==blog.getId()){
            blog.setCreateTime(new Date());
            blog.setUpdateTime(new Date());
            blog.setViews(0);
        }else{
            blog.setUpdateTime(new Date());
        }

        return blogRepository.save(blog);
    }

    @Override
    public Page<Blog> listBlog(Pageable pageable, BlogQuery blog) {
        return blogRepository.findAll(new Specification<Blog>() {
            @Override
            public Predicate toPredicate(Root<Blog> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
                //拼接动态查询
                List<Predicate> predicates = new ArrayList<>();
                if(!"".equals(blog.getTitle()) && null!=blog.getTitle()){
                    predicates.add(cb.like(root.<String>get("title"),"%"+blog.getTitle()+"%"));
                }
                if(null!=blog.getTypeId()){
                    predicates.add(cb.equal(root.<Type>get("type").get("id"),blog.getTypeId()));
                }
                if(blog.isRecommend()){
                    predicates.add(cb.equal(root.<Boolean>get("recommend"),blog.isRecommend()));
                }
                cq.where(predicates.toArray(new Predicate[predicates.size()]));
                return null;
            }
        },pageable);

    }

    @Override
    public Page<Blog> listBlog(Pageable pageable) {
        return blogRepository.findAll(pageable);
    }

    @Override
    public Page<Blog> listTagBlog(Pageable pageable, Long id) {
        return blogRepository.findAll(new Specification<Blog>() {
            @Override
            public Predicate toPredicate(Root root, CriteriaQuery cq, CriteriaBuilder cb) {
                Join join = root.join("tags");
                return cb.equal(join.get("id"),id);
            }
        },pageable);
    }

    @Override
    public List<Blog> listBlogTop(Integer size) {
        Sort sort = Sort.by(Sort.Direction.DESC,"updateTime");
        Pageable pageable = PageRequest.of(0,size,sort);
        return blogRepository.findTop(pageable);
    }


    @Transactional
    @Override
    public Blog updateBlog(Long id, Blog blog) {
        Blog b = blogRepository.getById(id);
        if(null==b){
            throw new notFoundException("抱歉！这个博客不存在");
        }
        //把更新blog时为空的属性重新赋值
        BeanUtils.copyProperties(blog,b, MyBeanUtils.gatNullPropertyNames(blog));
        b.setUpdateTime(new Date());
        return blogRepository.save(b);
    }

    @Transactional
    @Override
    public void deleteBlog(Long id) {
        blogRepository.deleteById(id);
    }

    @Override
    public Page<Blog> listByquery(String query, Pageable pageable) {
        return blogRepository.listByquery(query,pageable);
    }

    @Transactional
    @Override
    public Blog getblog(Long id) {
    Blog blog = blogRepository.getById(id);
    if(null==blog){
        throw new notFoundException("该博客不存在");
    }
    Blog b= new Blog();
    BeanUtils.copyProperties(blog,b);
    String content = b.getContent();
    b.setContent(MarkdownUtils.markdownToHtmlExtensions(content));
    blogRepository.updateviews(id);
    return b;
    }
}
