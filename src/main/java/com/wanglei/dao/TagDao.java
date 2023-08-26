package com.wanglei.dao;

import com.wanglei.pojo.BlogAndTag;
import com.wanglei.pojo.Tag;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TagDao {

    int saveTag(Tag tag);

    int deleteTagById(Long id);

    int updateTag(Tag tag);

    Tag getTagById(Long id);

    Tag getTagByName(String name);

    List<Tag> getAllTag();

    int saveIntoBlogAndTag(BlogAndTag blogAndTag);

    int deleteBlogAndTagById(Long id);

    List<Tag> selTagsByBlogId(Long blogId);
}
