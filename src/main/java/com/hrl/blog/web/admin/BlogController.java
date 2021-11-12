package com.hrl.blog.web.admin;


import com.hrl.blog.Service.BlogService;
import com.hrl.blog.Service.TagService;
import com.hrl.blog.Service.TypeService;
import com.hrl.blog.entities.Blog;
import com.hrl.blog.entities.User;
import com.hrl.blog.vo.BlogQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin")
public class BlogController {

    @Autowired
    private BlogService blogService;

    @Autowired
    private TypeService typeService;
    @Autowired
    private TagService tagService;

    @GetMapping("/blog")
    public String blogs(@PageableDefault(size = 5,sort = {"updateTime"},direction = Sort.Direction.DESC)
                                Pageable pageable, Model model, BlogQuery blog) {
        model.addAttribute("types",typeService.listType());
        model.addAttribute("page", blogService.listBlog(pageable, blog));
        return "admin/blog";
    }
    @PostMapping("/blog/search")
    public String search(@PageableDefault(size = 5,sort = {"updateTime"},direction = Sort.Direction.DESC)
                                Pageable pageable, Model model, BlogQuery blog) {
        model.addAttribute("page", blogService.listBlog(pageable, blog));
        return "admin/blog :: blogList";
    }

    @GetMapping("/blog/input")
    public String input(Model model){
        model.addAttribute("types",typeService.listType());
        model.addAttribute("tags",tagService.listTag());
        model.addAttribute("blog",new Blog());
        return "admin/blog-input";
    }

    @GetMapping("/blog/{id}/input")
    public String EditInput(@PathVariable Long id, Model model){
        model.addAttribute("types",typeService.listType());
        model.addAttribute("tags",tagService.listTag());
        Blog b = blogService.getBlog(id);
        b.init();
        model.addAttribute("blog",b);
        return "admin/blog-input";
    }

    @PostMapping("/blog")
    public String post(Blog blog, RedirectAttributes redirectAttributes, HttpSession session){
        blog.setUser((User) session.getAttribute("user"));
        blog.setType(typeService.getType(blog.getType().getId()));
        blog.setTags(tagService.listTag(blog.getTagIds()));
        Blog b;
        if(null==blog.getId()){
            b = blogService.saveBlog(blog);
        }else{
            b = blogService.updateBlog(blog.getId(),blog);
        }
        if(null==b){
            redirectAttributes.addFlashAttribute("message","操作成功");
        }else{
            redirectAttributes.addFlashAttribute("message","操作失败");
        }

        return "redirect:/admin/blog";
    }

    @GetMapping("/blog/{id}/delete")
    public String delete(@PathVariable Long id,RedirectAttributes redirectAttributes){
        blogService.deleteBlog(id);
        redirectAttributes.addFlashAttribute("message","删除成功！");
        return "redirect:/admin/blog";
    }


}
