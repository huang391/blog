package com.hrl.blog.web.admin;


import com.hrl.blog.Service.TagService;
import com.hrl.blog.entities.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/admin")
public class TagController {

    @Autowired
    private TagService tagService;

    @GetMapping("/tag")
    public String tag(@PageableDefault(size = 8,sort = {"id"},direction = Sort.Direction.DESC)
                                   Pageable pageable, Model model){
        model.addAttribute("page",tagService.listTag(pageable));
        return "admin/tag";
    }

    @GetMapping("/tag/input")
    public String input(Model model){
        model.addAttribute("tag",new Tag());
        return "admin/tag-input";
    }

    //编辑的页面和新增的页面公用，传递一个id
    @GetMapping("/tag/{id}/input")
    public String Editinput(@PathVariable Long id, Model model){
        model.addAttribute("tag",tagService.getTag(id));
        return "admin/tag-input";
    }


    @PostMapping("/tag")
    public String post(@Valid Tag tag, BindingResult bindingResult, RedirectAttributes redirectAttributes){
        Tag t1 = tagService.getTagByName(tag.getName());
        if(null != t1){
            //field要和实体类的名字保持一致
            bindingResult.rejectValue("name","nameError","该分类不能重复！");
        }
        if(bindingResult.hasErrors()){
            return "admin/tag-input";
        }
        Tag t = tagService.saveTag(tag);
        if(null==t){
            redirectAttributes.addFlashAttribute("message","新增失败！");
        }else{
            redirectAttributes.addFlashAttribute("message","新增成功！");
        }
        return "redirect:/admin/tag";
    }


    @PostMapping("/tag/{id}")
    public String EditPost(@Valid Tag tag, BindingResult bindingResult,@PathVariable Long id, RedirectAttributes redirectAttributes){
        Tag t1 = tagService.getTagByName(tag.getName());
        if(null!=t1){
            //field要和实体类的名字保持一致
            bindingResult.rejectValue("name","nameError","该分类不能重复！");
        }
        if(bindingResult.hasErrors()){
            return "admin/tag-input";
        }
        Tag t = tagService.updateTag(id,tag);
        if(null==t){
            redirectAttributes.addFlashAttribute("message","更新失败！");
        }else{
            redirectAttributes.addFlashAttribute("message","更新成功！");
        }
        return "redirect:/admin/tag";
    }

    @GetMapping("/tag/{id}/delete")
    public String delete(@PathVariable Long id,RedirectAttributes redirectAttributes){
        tagService.deleteTag(id);
        redirectAttributes.addFlashAttribute("message","删除成功！");
        return "redirect:/admin/tag";
    }


}
