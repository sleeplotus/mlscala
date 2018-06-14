package com.vrv.esper.example;

import org.junit.*;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class ClassForNameTest {

    @Test
    public void test() {
        try {
            Class c = Class.forName("java.util.ArrayList");
            Method[] methods = c.getMethods();
            Field[] field = c.getFields();
            java.util.ArrayList a = (java.util.ArrayList)c.newInstance();
            a.add("1");
            System.out.println(a.get(0));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }


}
