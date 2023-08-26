package com.wanglei.controller.after;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wanglei.pojo.Type;
import com.wanglei.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class TypeController {

    @Autowired
    private TypeService typeService;

    /**
     * 查询type列表页面
     * @param pageNum
     * @param model
     * @return
     */
    @GetMapping("/type-list")
    public String list(@RequestParam(defaultValue = "1", value = "pageNum") Integer pageNum, Model model){
        String orderBy = "id desc";
        PageHelper.startPage(pageNum, 3, orderBy);
        List<Type> typeList = typeService.listType();
        PageInfo<Type> pageInfo = new PageInfo<Type>(typeList);
        model.addAttribute("pageInfo", pageInfo);
        return "admin/type-list";
    }

    /**
     * 点击新增跳转路径
     * @return
     */
    @GetMapping("/type-list/type-input")
    public String input(Model model){
        model.addAttribute("type", new Type());
        return "admin/type-input";
    }

    /**
     * 点击编辑（修改）跳转路径。与新增共享一个页面，区别在于由编辑页面跳转会在model中携带id参数
     * @param id
     * @param model
     * @return
     */
    @GetMapping("/type-list/{id}/input")
    public String edit(@PathVariable Long id, Model model){
        model.addAttribute("type", typeService.getType(id));
        return "admin/type-input";
    }

    /**
     * 新增后保存路径
     * @param type
     * @param attributes
     * @return
     */
    @PostMapping("/saveType")
    public String post(Type type, RedirectAttributes attributes) {
        Type t = typeService.getTypeByName(type.getName());
        if (t != null) {
            attributes.addFlashAttribute("message", "不能添加重复的分类");
            return "redirect:/admin/type-list/type-input";
        }

        int flag = typeService.saveType(type);
        if(flag <= 0){
            attributes.addFlashAttribute("message", "操作失败");
        }else{
            attributes.addFlashAttribute("message", "操作成功");
        }
        return "redirect:/admin/type-list";
    }

    /**
     * 编辑后保存路径
     * @param type
     * @param id
     * @param attributes
     * @return
     */
    @PostMapping("/type-list/{id}")
    public String editPost(Type type,@PathVariable Long id, RedirectAttributes attributes) {
        Type t = typeService.getTypeByName(type.getName());
        if (t != null) {
            attributes.addFlashAttribute("message", "不能添加重复的分类");
            return "redirect:/admin/type-list/type-input";
        }

        int flag = typeService.updateType(id, type);
        if(flag <= 0){
            attributes.addFlashAttribute("message", "操作失败");
        }else{
            attributes.addFlashAttribute("message", "操作成功");
        }
        return "redirect:/admin/type-list";
    }

    /**
     * 删除路径
     * @param id
     * @param attributes
     * @return
     */
    @GetMapping("/type-list/{id}/delete")
    public String delete(@PathVariable Long id,RedirectAttributes attributes) {
        if(typeService.isDelType(id)){
            typeService.deleteType(id);
            attributes.addFlashAttribute("message", "删除成功");
        }else{
            attributes.addFlashAttribute("message", "操作失败！请先删除带有该分类的相关博客");
        }
        return "redirect:/admin/type-list";
    }
}
