package com.study.netty.proxy.simple;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Created by piguanghua on 2017/11/15.
 */
public class MyClassMain {
    public static void main(String [] args) throws Exception{
        MyClass myClass = new MyClass(0); //一般做法
        myClass.increase(2);
        System.out.println("Normal -> " + myClass.count);
        try {
            Constructor constructor = MyClass.class.getConstructor(int.class); //获取构造方法
            MyClass myClassReflect = (MyClass) constructor.newInstance(10); //创建对象
            Method method = MyClass.class.getMethod("increase", int.class);  //获取方法
            method.invoke(myClassReflect, 5); //调用方法
            Field field = MyClass.class.getField("count"); //获取域
            System.out.println("Reflect -> " + field.getInt(myClassReflect)); //获取域的值
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
