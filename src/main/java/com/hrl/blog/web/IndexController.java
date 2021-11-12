package com.hrl.blog.web;


import com.hrl.blog.Service.BlogService;
import com.hrl.blog.Service.TagService;
import com.hrl.blog.Service.TypeService;
import com.hrl.blog.entities.Blog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class IndexController {

    @Autowired
    private BlogService blogService;

    @Autowired
    private TypeService typeService;
    @Autowired
    private TagService tagService;

    @GetMapping("/")
    public String index(@PageableDefault(size = 8,sort = {"updateTime"},direction = Sort.Direction.DESC)
                                Pageable pageable, Model model, Blog blog) {
        model.addAttribute("types",typeService.listTopType(6));
        model.addAttribute("page", blogService.listBlog(pageable));
        model.addAttribute("tagTops", tagService.listTopTag(6));
        model.addAttribute("recommendsBlogs", blogService.listBlogTop(8));
        return "index";
    }

    @PostMapping("/search")
    public String search(@PageableDefault(size = 8,sort = {"updateTime"},direction = Sort.Direction.DESC)
                         @RequestParam String query, Pageable pageable, Model model) {
        model.addAttribute("page",blogService.listByquery("%"+query+"%",pageable));
        model.addAttribute("query",query);
        return "search";
    }

    @GetMapping("/blog/{id}")
    public String blog(@PathVariable Long id, Model model){
        model.addAttribute("blog",blogService.getblog(id));
        return "blog";
    }

    @GetMapping("/about")
    public String about(){
        return "about";
    }



}
