package com.wanglei.vo;

import com.wanglei.pojo.Type;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchBlog {

    private String title;
    private Long typeId;
    private Boolean recommend;

}
