package com.wanglei.controller.front;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wanglei.pojo.Tag;
import com.wanglei.pojo.Type;
import com.wanglei.service.BlogService;
import com.wanglei.service.TagService;
import com.wanglei.service.TypeService;
import com.wanglei.vo.DetailedBlog;
import com.wanglei.vo.FirstPageBlog;
import com.wanglei.vo.RecommendBlog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.List;

@Controller
public class IndexController {

    @Autowired
    private BlogService blogService;
    @Autowired
    private TypeService typeService;
    @Autowired
    private TagService tagService;

    @GetMapping("/")
    public String index(@RequestParam(defaultValue = "1",value = "pageNum") Integer pageNum, Model model){
        String orderBy = "update_time desc";
        PageHelper.startPage(pageNum, 10, orderBy);
        //查询博客列表
        List<FirstPageBlog> allFirstPageBlog = blogService.getAllFirstPageBlog();
        //查询最新推荐博客
        List<RecommendBlog> recommendedBlog = blogService.getRecommendedBlog();
        //查询分类
        List<Type> types = typeService.getTypeByUseAndNumber(6);
        //查询标签
        List<Tag> tags = tagService.getTagByUseAndNumber(6);

        PageInfo<FirstPageBlog> pageInfo = new PageInfo<>(allFirstPageBlog);
        model.addAttribute("pageInfo",pageInfo);
        model.addAttribute("recommendedBlogs", recommendedBlog);
        model.addAttribute("types", types);
        model.addAttribute("tags", tags);
        return "index";
    }

    @PostMapping("/search")
    public String search(@RequestParam(defaultValue = "1",value = "pageNum") Integer pageNum, Model model,@RequestParam String query){

        List<FirstPageBlog> searchBlog = blogService.queryBlog(query);
        PageInfo<FirstPageBlog> pageInfo = new PageInfo<>(searchBlog);
        model.addAttribute("pageInfo", pageInfo);
        model.addAttribute("query", query);
        return "search";
    }

    @GetMapping("/blog/{id}")
    public String blog(@PathVariable Long id, Model model){
        DetailedBlog detailedBlog = blogService.getDetailedBlog(id);
        model.addAttribute("blog", detailedBlog);
        return "blog";
    }
}
