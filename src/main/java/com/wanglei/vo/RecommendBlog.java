package com.wanglei.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RecommendBlog {

    private Long id;
    private String title;
    private boolean recommend;

}
