package com.hrl.blog.web;


import com.hrl.blog.Service.BlogService;
import com.hrl.blog.Service.TagService;
import com.hrl.blog.Service.TypeService;
import com.hrl.blog.entities.Tag;
import com.hrl.blog.entities.Type;
import com.hrl.blog.vo.BlogQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class TagsController {


    @Autowired
    private TagService tagService;

    @Autowired
    private BlogService blogService;

    @GetMapping("/tags/{id}")
    public String type(@PageableDefault(size = 8,sort = {"updateTime"},direction = Sort.Direction.DESC)
                       @PathVariable Long id, Pageable pageable, Model model) {
        List<Tag> tags = tagService.listTopTag(1000);
        if(id==-1){
            id = tags.get(0).getId();
        }
        model.addAttribute("tags" ,tags);
        model.addAttribute("page" ,blogService.listTagBlog(pageable,id));
        model.addAttribute("active",id);
        return "tags";
    }
}
