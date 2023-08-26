package com.wanglei.dao;

import com.wanglei.pojo.Comment;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CommentDao {

    List<Comment> getComsByBlogId(Long blogId);

    List<Comment> getComsByParentComment(Long id);

    int saveComment(Comment comment);
}
