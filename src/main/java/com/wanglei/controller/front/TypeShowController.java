package com.wanglei.controller.front;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wanglei.pojo.Type;
import com.wanglei.service.BlogService;
import com.wanglei.service.TypeService;
import com.wanglei.vo.FirstPageBlog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class TypeShowController {

    @Autowired
    private TypeService typeService;

    @Autowired
    private BlogService blogService;

    //    分页查询分类
    @GetMapping("/types/{id}")
    public String types(@RequestParam(defaultValue = "1",value = "pageNum") Integer pageNum, @PathVariable Long id, Model model) {

        PageHelper.startPage(pageNum, 100);
        List<Type> types = typeService.getTypeByUseAndNumber(Integer.MAX_VALUE);

        //id为-1表示从首页导航栏点击进入分类页面
        if (id == -1) {
            if(!types.isEmpty()){
                id = types.get(0).getId();
            }
        }
        List<FirstPageBlog> blogs = blogService.getFirstPageBlogByTypeId(id);
        PageInfo<FirstPageBlog> pageInfo = new PageInfo<>(blogs);

        model.addAttribute("types", types);
        model.addAttribute("pageInfo", pageInfo);
        model.addAttribute("activeTypeId", id);
        return "types";
    }
}
