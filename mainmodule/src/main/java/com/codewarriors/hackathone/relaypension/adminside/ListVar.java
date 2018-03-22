package com.codewarriors.hackathone.relaypension.adminside;

/**
 * Created by aishwarya on 21-03-2018.
 */

public class ListVar {
    String name,consti;
    int age,fno;

    public ListVar(String name, int age, String consti, int fno) {
        this.name = name;
        this.age = age;
        this.consti = consti;
        this.fno = fno;
    }

    public String getName() {
        return name;
    }

    public String getConsti() {
        return consti;
    }

    public int getAge() {
        return age;
    }

    public int getFno() {
        return fno;
    }
}
