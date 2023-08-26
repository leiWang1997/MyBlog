package com.wanglei.service;

import com.wanglei.pojo.Tag;

import java.util.List;

public interface TagService {

    int saveTag(Tag tag);

    boolean isDelTag(Long tagId);

    void deleteTag(Long id);

    int updateTag(Long id, Tag tag);

    Tag getTag(Long id);

    Tag getTagByName(String name);

    List<Tag> listTag();

    void saveBlogAndTag(Long blogId, List<Long> tags);

    void updateBlogAndTag(Long blogId, List<Long> tags);

    List<Tag> getTagByUseAndNumber(int number);
}
