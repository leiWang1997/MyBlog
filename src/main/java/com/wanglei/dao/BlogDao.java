package com.wanglei.dao;

import com.wanglei.pojo.Blog;
import com.wanglei.vo.DetailedBlog;
import com.wanglei.vo.FirstPageBlog;
import com.wanglei.vo.RecommendBlog;
import com.wanglei.vo.SearchBlog;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface BlogDao {

    Long saveBlog(Blog blog);

    int deleteBlogById(Long id);

    int updateBlog(Blog blog);

    Blog getBlogById(Long id);

    List<Blog> getBlogBySearch(SearchBlog searchBlog);

    List<Blog> getAllBlog();

    int countBlogsByTagId(Long tagId);

    int countBlogsByTypeId(Long typeId);

    int delTagsByBlog(Long blogId);

    List<FirstPageBlog> getFirstPageBlog();

    List<RecommendBlog> getRecommendBlog();

    List<Blog> getBlogsByTagId(Long tagId);

    List<Blog> getBlogsByTypeId(Long typeId);

    List<FirstPageBlog> queryBlog(@Param("query") String query);

    DetailedBlog getDetailedBlog(Long id);

    //文章访问更新
    int updateViews(Long id);

    List<FirstPageBlog> getFirstPageBlogByTypeId(Long typeId);

    List<FirstPageBlog> getFirstPageBlogByTagId(Long tagId);
}
