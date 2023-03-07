package com.cohelp.server.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zgy
 * @create 2023-03-05 11:40
 */
public class PageUtil {
    public static <T> List<T> pageByList(List<T> list,Integer pageNum,Integer pageSize){
        if(list==null||list.isEmpty()){
            return new ArrayList();
        }
        int total = list.size();
        int startIndex = (pageNum-1) * pageSize;
        if(startIndex>total){
            return new ArrayList();
        }
        int endIndex = Math.min(startIndex + pageSize,total);
        List subList = list.subList(startIndex, endIndex);
        return subList;
    }
}
