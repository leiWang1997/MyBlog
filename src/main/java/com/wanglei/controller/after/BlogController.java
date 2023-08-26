package com.wanglei.controller.after;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wanglei.pojo.Blog;
import com.wanglei.pojo.Tag;
import com.wanglei.pojo.Type;
import com.wanglei.pojo.User;
import com.wanglei.service.BlogService;
import com.wanglei.service.TagService;
import com.wanglei.service.TypeService;
import com.wanglei.util.StringUtils;
import com.wanglei.vo.SearchBlog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class BlogController {

    @Autowired
    private BlogService blogService;
    @Autowired
    private TypeService typeService;
    @Autowired
    private TagService tagService;

    @GetMapping("/blog-list")
    public String list(Model model,@RequestParam(defaultValue = "1",value = "pageNum") Integer pageNum){

        String orderBy = "update_time desc";
        PageHelper.startPage(pageNum, 3, orderBy);
        List<Blog> list = blogService.listBlog();
        PageInfo<Blog> pageInfo = new PageInfo<Blog>(list);
        model.addAttribute("types",typeService.listType());
        model.addAttribute("tags",tagService.listTag());
        model.addAttribute("pageInfo",pageInfo);
        return "admin/blog-list";
    }

    //搜索博客管理列表
    @PostMapping("/blog-list/search")
    public String search(SearchBlog searchBlog, Model model,
                         @RequestParam(defaultValue = "1",value = "pageNum") Integer pageNum) {
        PageHelper.startPage(pageNum, 3);
        List<Blog> blogBySearch = blogService.listBlogBySearch(searchBlog);
        PageInfo<Blog> pageInfo = new PageInfo<>(blogBySearch);
        model.addAttribute("pageInfo", pageInfo);
        return "admin/blog-list :: blogList";
    }

    @GetMapping("/blog-list/blog-input")
    public String input(Model model){
        model.addAttribute("types",typeService.listType());
        model.addAttribute("tags",tagService.listTag());
        model.addAttribute("blog", new Blog());
        return "admin/blog-input";
    }

    //博客新增
    @PostMapping("/saveBlog")
    public String post(Blog blog, RedirectAttributes attributes, HttpSession session){
        //新增的时候需要传递blog对象，blog对象需要有user
        blog.setUser((User)session.getAttribute("user"));
        //设置用户id
        blog.setUserId(blog.getUser().getId());
        //设置blog的type
        blog.setType(typeService.getType(blog.getType().getId()));
        //设置blog中typeId属性
        blog.setTypeId(blog.getType().getId());

        Long b = blogService.saveBlog(blog);

        StringUtils stringUtils = new StringUtils();
        List<Long> ids = stringUtils.convertToList(blog.getTagIds());
        List<Tag> tags = new ArrayList<>();
        for(Long id : ids){
            tags.add(tagService.getTag(id));
        }
        blog.setTags(tags);
        tagService.saveBlogAndTag(blog.getId(), ids);

        if(b <= 0){
            attributes.addFlashAttribute("message", "新增失败");
        }else {
            attributes.addFlashAttribute("message", "新增成功");
        }
        return "redirect:/admin/blog-list";
    }

    //跳转编辑修改文章
    @GetMapping("/blog-list/{id}/input")
    public String edit(@PathVariable Long id, Model model) {
        model.addAttribute("types",typeService.listType());
        model.addAttribute("tags",tagService.listTag());
        Blog b = blogService.getBlog(id);
        List<Tag> list = blogService.listTagByBlogId(id);
        b.setTags(list);
        b.init();
        model.addAttribute("blog",b);
        return "admin/blog-input";
    }

    //博客新增
    @PostMapping("/blog-list/{id}")
    public String editPost(@PathVariable Long id, Blog blog, RedirectAttributes attributes, HttpSession session){

        blog.setType(typeService.getType(blog.getType().getId()));
        //设置blog中typeId属性
        blog.setTypeId(blog.getType().getId());

        int b = blogService.updateBlog(id, blog);

        StringUtils stringUtils = new StringUtils();
        List<Long> ids = stringUtils.convertToList(blog.getTagIds());
        List<Tag> tags = new ArrayList<>();
        for(Long d : ids){
            tags.add(tagService.getTag(d));
        }
        blog.setTags(tags);
        tagService.updateBlogAndTag(blog.getId(), ids);

        if(b <= 0){
            attributes.addFlashAttribute("message", "编辑失败");
        }else {
            attributes.addFlashAttribute("message", "编辑成功");
        }
        return "redirect:/admin/blog-list";
    }

    @GetMapping("/blog-list/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes attributes){
        blogService.deleteBlog(id);
        blogService.delTagsByBlog(id);
        attributes.addFlashAttribute("message", "删除成功");
        return "redirect:/admin/blog-list";
    }
}
