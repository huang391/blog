package com.hrl.blog.Util;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import java.beans.PropertyDescriptor;
import java.util.ArrayList;
import java.util.List;

//获取所有属性为空属性名的数组
public class MyBeanUtils {

    public static String[] gatNullPropertyNames(Object obj){
        BeanWrapper beanWrapper = new BeanWrapperImpl(obj);
        PropertyDescriptor[] pds = beanWrapper.getPropertyDescriptors();
        List<String> list = new ArrayList<>();
        for (PropertyDescriptor pd : pds){
            String Name = pd.getName();
            if(null==beanWrapper.getPropertyValue(Name)){
                list.add(Name);
            }
        }
        return list.toArray(new String[list.size()]);
    }
}
