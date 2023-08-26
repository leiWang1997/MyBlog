package com.wanglei.service.impl;

import com.wanglei.dao.BlogDao;
import com.wanglei.dao.TypeDao;
import com.wanglei.exception.NotFoundException;
import com.wanglei.pojo.Type;
import com.wanglei.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Service
public class TypeServiceImpl implements TypeService {

    @Autowired
    private TypeDao typeDao;
    @Autowired
    private BlogDao blogDao;

    @Override
    public int saveType(Type type) {
        return typeDao.saveType(type);
    }

    @Override
    public boolean isDelType(Long typeId) {
        return blogDao.countBlogsByTypeId(typeId) == 0 ? true : false;
    }

    @Override
    public List<Type> getTypeByUseAndNumber(int number) {
        List<Type> allType = typeDao.getAllType();

        for (Type type:
             allType) {
            type.setBlogs(blogDao.getBlogsByTypeId(type.getId()));
        }

        //按（博客数目， 从大到小）：排列types
        Collections.sort(allType, new Comparator<Type>() {
            @Override
            public int compare(Type o1, Type o2) {
                return o2.getBlogs().size() - o1.getBlogs().size();
            }
        });

        if(allType.size() > number){
            List<Type> types = new ArrayList<>(number);
            for (int i = 0; i < number; i++) {
                types.add(i, allType.get(i));
            }
            return types;
        }

        return allType;
    }

    @Override
    public void deleteType(Long id) {
        typeDao.deleteTypeById(id);
    }

    @Override
    public int updateType(Long id, Type type) {
        Type t = typeDao.getTypeById(id);
        if(t == null){
            throw new NotFoundException("不存在该类型");
        }else{
            return typeDao.updateType(type);
        }
    }

    @Override
    public Type getType(Long id) {
        return typeDao.getTypeById(id);
    }

    @Override
    public Type getTypeByName(String name) {
        return typeDao.getTypeByName(name);
    }

    @Override
    public List<Type> listType() {
        return typeDao.getAllType();
    }


}
