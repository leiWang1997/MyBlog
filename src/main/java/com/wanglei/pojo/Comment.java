package com.wanglei.pojo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor      //无参构造
@AllArgsConstructor
public class Comment {

    private Long id;
    private String nickname;
    private String email;
    private String content;
    private String avatar;
    private Date createTime;
    private List<Comment> replyComments = new ArrayList<>();   //一个评论父类对象 ==>> 对应多条评论子类对象
    private Comment parentComment;  //多个子类 ==>>> 对应一个父类
    private Blog blog;              //多条评论  ==>>> 1篇博客
    private boolean adminComment;   //true为博主评论， false则不是

}
