package com.wanglei.dao;

import com.wanglei.pojo.Type;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TypeDao {

    int saveType(Type type);

    int deleteTypeById(Long id);

    int updateType(Type type);

    Type getTypeById(Long id);

    Type getTypeByName(String name);

    List<Type> getAllType();

}
