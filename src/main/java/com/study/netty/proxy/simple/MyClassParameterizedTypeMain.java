package com.study.netty.proxy.simple;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * Created by piguanghua on 2017/11/15.
 * 通过反射获取泛型泛型参数类型
 */
public class MyClassParameterizedTypeMain {
    public static void main(String [] args) throws Exception{
        Field field = MyClass.class.getDeclaredField("myList"); //myList的类型是List
        Type type = field.getGenericType();
        if (type instanceof ParameterizedType) {
            ParameterizedType paramType = (ParameterizedType) type;
            Type[] actualTypes = paramType.getActualTypeArguments();
            for (Type aType : actualTypes) {
                if (aType instanceof Class) {
                    Class clz = (Class) aType;
                    System.out.println(clz.getName()); //输出java.lang.String
                }
            }
        }
    }
}
