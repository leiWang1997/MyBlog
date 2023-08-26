package com.wanglei.service.impl;

import com.wanglei.dao.BlogDao;
import com.wanglei.dao.TagDao;
import com.wanglei.dao.TypeDao;
import com.wanglei.exception.NotFoundException;
import com.wanglei.pojo.Blog;
import com.wanglei.pojo.Tag;
import com.wanglei.pojo.Type;
import com.wanglei.service.BlogService;
import com.wanglei.util.MarkdownUtils;
import com.wanglei.vo.DetailedBlog;
import com.wanglei.vo.FirstPageBlog;
import com.wanglei.vo.RecommendBlog;
import com.wanglei.vo.SearchBlog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.List;

@Service
public class BlogServiceImpl implements BlogService {

    @Autowired
    private BlogDao blogDao;
    @Autowired
    private TypeDao typeDao;
    @Autowired
    private TagDao tagDao;

    @Override
    public Long saveBlog(Blog blog) {
        blog.setCreateTime(new Date());
        blog.setUpdateTime(new Date());
        blog.setViews(0);
        return blogDao.saveBlog(blog);
    }

    @Override
    public void deleteBlog(Long id) {
        blogDao.deleteBlogById(id);
    }

    @Override
    public int updateBlog(Long id, Blog blog) {
        Blog b = blogDao.getBlogById(id);
        if(b == null){
            throw new NotFoundException("该博客不存在");
        }else {
            blog.setUpdateTime(new Date());
            return blogDao.updateBlog(blog);
        }
    }

    @Override
    public Blog getBlog(Long id) {
        return blogDao.getBlogById(id);
    }

    @Override
    public List<Blog> listBlogBySearch(SearchBlog searchBlog) {
        return blogDao.getBlogBySearch(searchBlog);
    }


    @Override
    public List<Blog> listBlog() {
        List<Blog> list = blogDao.getAllBlog();
        for(Blog blog :  list){
            Type t = typeDao.getTypeById(blog.getTypeId());
            blog.setType(t);
        }
        return list;
    }

    @Override
    public List<Tag> listTagByBlogId(Long blogId){
        List<Tag> list = tagDao.selTagsByBlogId(blogId);
        return list;
    }

    @Override
    public int delTagsByBlog(Long blogId) {
        return blogDao.delTagsByBlog(blogId);
    }

    @Override
    public List<FirstPageBlog> getAllFirstPageBlog() {
        return blogDao.getFirstPageBlog();
    }

    @Override
    public List<RecommendBlog> getRecommendedBlog() {
        return blogDao.getRecommendBlog();
    }

    @Override
    public List<FirstPageBlog> queryBlog(String query) {
        return blogDao.queryBlog(query);
    }

    @Override
    public DetailedBlog getDetailedBlog(Long id) {
        DetailedBlog detailedBlog =  blogDao.getDetailedBlog(id);
        if (detailedBlog == null) {
            throw new NotFoundException("该博客不存在");
        }
        String content = detailedBlog.getContent();
        detailedBlog.setContent(MarkdownUtils.markdownToHtmlExtensions(content));
        //文章访问数量自增
        blogDao.updateViews(id);
        Type type = typeDao.getTypeById(id);
        List<Tag> tags = tagDao.selTagsByBlogId(id);
        detailedBlog.setType(type);
        detailedBlog.setTags(tags);
        return detailedBlog;
    }

    @Override
    public List<FirstPageBlog> getFirstPageBlogByTypeId(Long typeId) {
        return blogDao.getFirstPageBlogByTypeId(typeId);
    }

    @Override
    public List<FirstPageBlog> getFirstPageBlogByTagId(Long tagId) {
        return blogDao.getFirstPageBlogByTagId(tagId);
    }


}
