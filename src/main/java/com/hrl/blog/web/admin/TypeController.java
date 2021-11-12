package com.hrl.blog.web.admin;


import com.hrl.blog.Service.TypeService;
import com.hrl.blog.entities.Type;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.data.domain.Pageable;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/admin")
public class TypeController {

    @Autowired
    private TypeService typeService;

    @GetMapping("/type")
    public String type(@PageableDefault(size = 8,sort = {"id"},direction = Sort.Direction.DESC)
                                   Pageable pageable, Model model){
        model.addAttribute("page",typeService.listType(pageable));
        return "admin/type";
    }

    @GetMapping("/type/input")
    public String input(Model model){
        model.addAttribute("type",new Type());
        return "admin/type-input";
    }

    //编辑的页面和新增的页面公用，传递一个id
    @GetMapping("/type/{id}/input")
    public String EditInput(@PathVariable Long id, Model model){
        model.addAttribute("type",typeService.getType(id));
        return "admin/type-input";
    }


    @PostMapping("/type")
    public String post(@Valid Type type, BindingResult bindingResult, RedirectAttributes redirectAttributes){
        Type t1 = typeService.getTypeByName(type.getName());
        if(null!=t1){
            //field要和实体类的名字保持一致
            bindingResult.rejectValue("name","nameError","该分类不能重复！");
        }
        if(bindingResult.hasErrors()){
            return "admin/type-input";
        }
        Type t = typeService.saveType(type);
        if(null==t){
            redirectAttributes.addFlashAttribute("message","新增失败！");
        }else{
            redirectAttributes.addFlashAttribute("message","新增成功！");
        }
        return "redirect:/admin/type";
    }


    @PostMapping("/type/{id}")
    public String EditPost(@Valid Type type, BindingResult bindingResult,@PathVariable Long id, RedirectAttributes redirectAttributes){
        Type t1 = typeService.getTypeByName(type.getName());
        if(null!=t1){
            //field要和实体类的名字保持一致
            bindingResult.rejectValue("name","nameError","该分类不能重复！");
        }
        if(bindingResult.hasErrors()){
            return "admin/type-input";
        }
        Type t = typeService.updateType(id,type);
        if(null==t){
            redirectAttributes.addFlashAttribute("message","更新失败！");
        }else{
            redirectAttributes.addFlashAttribute("message","更新成功！");
        }
        return "redirect:/admin/type";
    }

    @GetMapping("/type/{id}/delete")
    public String delete(@PathVariable Long id,RedirectAttributes redirectAttributes){
        typeService.deleteType(id);
        redirectAttributes.addFlashAttribute("message","删除成功！");
        return "redirect:/admin/type";
    }


}
