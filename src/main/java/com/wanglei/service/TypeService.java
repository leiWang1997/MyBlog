package com.wanglei.service;

import com.wanglei.pojo.Type;

import java.util.List;

public interface TypeService {

    int saveType(Type type);

    void deleteType(Long id);

    int updateType(Long id, Type type);

    Type getType(Long id);

    Type getTypeByName(String name);

    List<Type> listType();

    boolean isDelType(Long typeId);

    List<Type> getTypeByUseAndNumber(int number);
}
