package com.wanglei.service;

import com.wanglei.pojo.Blog;
import com.wanglei.pojo.Tag;
import com.wanglei.vo.DetailedBlog;
import com.wanglei.vo.FirstPageBlog;
import com.wanglei.vo.RecommendBlog;
import com.wanglei.vo.SearchBlog;

import java.util.List;

public interface BlogService {

    Long saveBlog(Blog blog);

    void deleteBlog(Long id);

    int updateBlog(Long id, Blog blog);

    Blog getBlog(Long id);

    List<Blog> listBlogBySearch(SearchBlog searchBlog);

    List<Blog> listBlog();

    List<Tag> listTagByBlogId(Long blogId);

    int delTagsByBlog(Long blogId);

    List<FirstPageBlog> getAllFirstPageBlog();

    List<RecommendBlog> getRecommendedBlog();

    List<FirstPageBlog> queryBlog(String query);

    DetailedBlog getDetailedBlog(Long id);

    List<FirstPageBlog> getFirstPageBlogByTypeId(Long typeId);

    List<FirstPageBlog> getFirstPageBlogByTagId(Long tagId);
}
