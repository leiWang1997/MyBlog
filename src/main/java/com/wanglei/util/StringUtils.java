package com.wanglei.util;

import java.util.ArrayList;
import java.util.List;

public class StringUtils {

    public List<Long> convertToList(String ids){
        List<Long> list = new ArrayList<>();
        if(ids != null && !"".equals(ids)){
            String[] idArray = ids.split(",");
            for(int i = 0; i < idArray.length; i++){
                Long t = Long.parseLong(idArray[i]);
                list.add(t);
            }
        }
        return list;
    }
}
