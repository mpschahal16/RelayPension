package com.codewarriors.hackathone.relaypension.adminside.tabactivitypack;

/**
 * Created by aishwarya on 21-03-2018.
 */

public class ApplicationFormListVAR {
    private String name,consti,age,fno;


    public ApplicationFormListVAR(String name, String age, String consti, String fno) {
        this.name = name;
        this.age = age;
        this.consti = consti;
        this.fno = fno;
    }

    public String getName()
    {
        return name;
    }

    public String getConsti() {
        return consti;
    }

    public String getAge() {
        return age;
    }

    public String getFno() {
        return fno;
    }
}
