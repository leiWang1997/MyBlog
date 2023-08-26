package com.wanglei.controller.after;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wanglei.pojo.Tag;
import com.wanglei.pojo.Type;
import com.wanglei.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;


@Controller
@RequestMapping("/admin")
public class TagController {
    @Autowired
    TagService tagService;

    @GetMapping("/tag-list")
    public String list(@RequestParam(defaultValue = "1", value = "pageNum") Integer pageNum, Model model) {
        String orderBy = "id desc";
        PageHelper.startPage(pageNum, 3, orderBy);
        List<Tag> tagList = tagService.listTag();
        PageInfo<Tag> pageInfo = new PageInfo<Tag>(tagList);
        model.addAttribute("pageInfo", pageInfo);
        return "admin/tag-list";
    }

    @GetMapping("/tag-list/tag-input")
    public String input(Model model) {
        model.addAttribute("tag", new Tag());
        return "admin/tag-input";
    }

    @GetMapping("/tag-list/{id}/input")
    public String edit (@PathVariable Long id, Model model){
        model.addAttribute("tag", tagService.getTag(id));
        return "admin/tag-input";
    }

    @PostMapping("/saveTag")
    public String post (Tag tag, RedirectAttributes attributes){
        Tag t = tagService.getTagByName(tag.getName());
        if (t != null) {
            attributes.addFlashAttribute("message", "不能添加重复的标签");
            return "redirect:/admin/tag-list/tag-input";
        }

        int flag = tagService.saveTag(tag);
        if(flag <= 0){
            attributes.addFlashAttribute("message", "操作失败");
        }else{
            attributes.addFlashAttribute("message", "操作成功");
        }

        return "redirect:/admin/tag-list";
    }

    @PostMapping("/tag-list/{id}")
    public String editPost (Tag tag, @PathVariable Long id, RedirectAttributes attributes){
        Tag t = tagService.getTagByName(tag.getName());
        if (t != null) {
            attributes.addFlashAttribute("message", "不能添加重复的标签");
            return "redirect:/admin/tag-list/tag-input";
        }

        int flag = tagService.updateTag(id, tag);
        if(flag <= 0){
            attributes.addFlashAttribute("message", "操作失败");
        }else{
            attributes.addFlashAttribute("message", "操作成功");
        }

        return "redirect:/admin/tag-list";
    }

    @GetMapping("/tag-list/{id}/delete")
    public String delete (@PathVariable Long id, RedirectAttributes attributes){
        if(tagService.isDelTag(id)){
            tagService.deleteTag(id);
            attributes.addFlashAttribute("message", "删除成功");
        }else{
            attributes.addFlashAttribute("message", "操作失败！请先删除带有该标签的相关博客");
        }
        return "redirect:/admin/tag-list";
    }
}
