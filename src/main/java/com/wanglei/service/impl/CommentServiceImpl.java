package com.wanglei.service.impl;

import com.wanglei.dao.CommentDao;
import com.wanglei.pojo.Comment;
import com.wanglei.service.CommentService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentDao commentDao;

    @Override
    public List<Comment> listCommentByBlogId(Long blogId) {

        List<Comment> allComment = commentDao.getComsByBlogId(blogId);
        List<Comment> comments = new ArrayList<>();

        for(Comment comment : allComment){
            if(comment.getParentComment().getId() == -1){
                comments.add(comment);
            }
        }

        for(Comment comment : comments){
            List<Comment> twoComment = new ArrayList<>();
            recursion(twoComment, comment);
            comment.setReplyComments(twoComment);
        }

        return comments;
    }

    private void recursion(List<Comment> twoComment, Comment comment){

        List<Comment> sonComment = commentDao.getComsByParentComment(comment.getId());
        if(sonComment.size() > 0){
            for(Comment son : sonComment){
                son.getParentComment().setNickname(comment.getNickname());
                twoComment.add(son);
                recursion(twoComment, son);
            }
        }

    }

    @Override
    public int saveComment(Comment comment) {

        comment.setCreateTime(new Date());
        commentDao.saveComment(comment);
        return 0;
    }

    @Override
    public void deleteComment(Comment comment, Long id) {

    }

}
