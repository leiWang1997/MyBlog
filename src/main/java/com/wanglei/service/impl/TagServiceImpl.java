package com.wanglei.service.impl;

import com.wanglei.dao.BlogDao;
import com.wanglei.dao.TagDao;
import com.wanglei.exception.NotFoundException;
import com.wanglei.pojo.Blog;
import com.wanglei.pojo.BlogAndTag;
import com.wanglei.pojo.Tag;
import com.wanglei.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


@Service
public class TagServiceImpl implements TagService {
    @Autowired
    private TagDao tagDao;
    @Autowired
    private BlogDao blogDao;

    @Override
    public int saveTag(Tag tag) {
        return tagDao.saveTag(tag);
    }

    @Override
    public boolean isDelTag(Long tagId){
        return blogDao.countBlogsByTagId(tagId) == 0 ? true : false;
    }

    @Override
    public void deleteTag(Long id) {

        tagDao.deleteTagById(id);
    }

    @Override
    public int updateTag(Long id, Tag tag) {
        Tag t = tagDao.getTagById(id);
        if(t == null){
            throw new NotFoundException("不存在该标签");
        }else{
            return tagDao.updateTag(tag);
        }
    }

    @Override
    public Tag getTag(Long id) {
        return tagDao.getTagById(id);
    }

    @Override
    public Tag getTagByName(String name) {
        return tagDao.getTagByName(name);
    }

    @Override
    public List<Tag> listTag() {
        return tagDao.getAllTag();
    }

    //1、添加：通过blogId、tags[] 一组id, 添加到blog_tag表中
    @Override
    public void saveBlogAndTag(Long blogId, List<Long> tags) {

        BlogAndTag blogAndTag = new BlogAndTag();
        blogAndTag.setBlogId(blogId);

        for (Long tagId:tags){
            blogAndTag.setTagId(tagId);
            tagDao.saveIntoBlogAndTag(blogAndTag);//通过blogAndTag， 添加blogAndTag表数据
        }
    }

    @Override
    public void updateBlogAndTag(Long blogId, List<Long> tags) {
        tagDao.deleteBlogAndTagById(blogId);
        BlogAndTag blogAndTag = new BlogAndTag();
        blogAndTag.setBlogId(blogId);

        for (Long tagId:tags){
            blogAndTag.setTagId(tagId);
            tagDao.saveIntoBlogAndTag(blogAndTag);//通过blogAndTag， 添加blogAndTag表数据
        }
    }

    @Override
    public List<Tag> getTagByUseAndNumber(int number) {

        List<Tag> allTag = tagDao.getAllTag();

        for (Tag tag:
                allTag) {
            List<Blog> blogs = blogDao.getBlogsByTagId(tag.getId());
            if(blogs.size() > 0){
                tag.setBlogs(blogs);
            }
        }

        //按（博客数目， 从大到小）：排列tags
        Collections.sort(allTag, new Comparator<Tag>() {
            @Override
            public int compare(Tag o1, Tag o2) {
                return o2.getBlogs().size() - o1.getBlogs().size();
            }
        });

        if(allTag.size() > number){
            List<Tag> tags = new ArrayList<>(number);
            for (int i = 0; i < number; i++) {
                tags.add(i, allTag.get(i));
            }
            return tags;
        }

        return allTag;

    }
}
