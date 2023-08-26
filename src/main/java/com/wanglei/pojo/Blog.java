package com.wanglei.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Blog {

    private Long id;
    private String title;
    private String content;
    private String firstPicture;
    private String flag;
    private Integer views;
    private boolean appreciation;
    private boolean shareStatement;
    private boolean commentabled;
    private boolean published;
    private boolean recommend;
    private Date createTime;
    private Date updateTime;
    private String description;         //博客描述
    private User user;                  //多篇博客 ==>>> 一个用户
    private Type type;                  //多篇博客 ===>>> 一个类型
    private Long typeId;
    private Long userId;

    private List<Tag> tags = new ArrayList<>();     //多篇博客 <==> 多个标签
    private String tagIds;              //用于存放：前端传来的（标签索引）例如：1, 2, 3
    private List<Comment> comments = new ArrayList<>();     //一篇博客 ==>> 多条评论
    private Integer commentNum;

    public void init(){
        this.tagIds = tagsToIds(this.tags);
    }

    private String tagsToIds(List<Tag> tags){
        if(!tags.isEmpty()){
            StringBuilder ids = new StringBuilder();
            boolean flag = false;
            for(Tag tag : tags){
                if(flag){
                    ids.append(",");
                }else{
                    flag = true;
                }
                ids.append(tag.getId());
            }
            return ids.toString();
        }else{
            return tagIds;
        }
    }
}