package com.wanglei.controller.front;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wanglei.pojo.Tag;
import com.wanglei.service.BlogService;
import com.wanglei.service.TagService;
import com.wanglei.vo.FirstPageBlog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class TagShowController {

    @Autowired
    private TagService tagService;

    @Autowired
    private BlogService blogService;

    //    分页查询分类
    @GetMapping("/tags/{id}")
    public String tags(@RequestParam(defaultValue = "1",value = "pageNum") Integer pageNum, @PathVariable Long id, Model model) {

        PageHelper.startPage(pageNum, 100);
        List<Tag> tags = tagService.getTagByUseAndNumber(Integer.MAX_VALUE);

        //id为-1表示从首页导航栏点击进入分类页面
        if (id == -1) {
            if(!tags.isEmpty()){
                id = tags.get(0).getId();
            }
        }
        List<FirstPageBlog> blogs = blogService.getFirstPageBlogByTagId(id);
        PageInfo<FirstPageBlog> pageInfo = new PageInfo<>(blogs);

        model.addAttribute("tags", tags);
        model.addAttribute("pageInfo", pageInfo);
        model.addAttribute("activeTagId", id);
        return "tags";
    }
}
