package com.hrl.blog.web;


import com.hrl.blog.Service.BlogService;
import com.hrl.blog.Service.TypeService;
import com.hrl.blog.entities.Blog;
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
public class TypesController {


    @Autowired
    private TypeService typeService;

    @Autowired
    private BlogService blogService;

    @GetMapping("/types/{id}")
    public String type(@PageableDefault(size = 8,sort = {"updateTime"},direction = Sort.Direction.DESC)
                        @PathVariable Long id, Pageable pageable, Model model) {
        List<Type> types = typeService.listTopType(1000);
        if(id==-1){
            id = types.get(0).getId();
        }
        BlogQuery blog = new BlogQuery();
        blog.setTypeId(id);
        model.addAttribute("types" ,types);
        model.addAttribute("page" ,blogService.listBlog(pageable,blog));
        model.addAttribute("active",id);
        return "types";
    }
}
