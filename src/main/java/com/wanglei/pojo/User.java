package com.wanglei.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor      //无参构造
@AllArgsConstructor     //有参构造
public class User {

    private Long id;
    private String nickname;
    private String name;
    private String password;
    private String email;
    private String avatar;
    private Integer type;
    private Date creatTime;
    private Date updateTime;
    private List<Blog> blogs = new ArrayList<>();

}
