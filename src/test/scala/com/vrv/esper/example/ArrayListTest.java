package com.vrv.esper.example;

import org.junit.Test;

import java.util.ArrayList;

public class ArrayListTest {

    @Test
    public void test() {
        ArrayList<String> list = new ArrayList();
        list.add("aaa");
        list.add("bbb");
        list.toArray();
        String[] array = (String[]) list.toArray(new String[list.size()]);
    }

}
