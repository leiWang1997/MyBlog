package com.wanglei.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor      //无参构造
@AllArgsConstructor     //有参构造
public class Type {

    private Long id;
    private String name;
    private List<Blog> blogs;           //一个类型（对应）多篇博客
}
