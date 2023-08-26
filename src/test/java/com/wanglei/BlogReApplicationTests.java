package com.wanglei;

import com.wanglei.dao.BlogDao;
import com.wanglei.service.TypeService;
import com.wanglei.vo.DetailedBlog;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.net.SocketImpl;

@SpringBootTest
class BlogReApplicationTests {

    @Autowired
    private BlogDao blogDao;

    @Test
    void testgetDetailedBlog() {

        DetailedBlog db = blogDao.getDetailedBlog(1121L);
        System.out.println(db.getTitle());
        System.out.println(db.getNickname());
        System.out.println(db.isAppreciation());

    }

}
