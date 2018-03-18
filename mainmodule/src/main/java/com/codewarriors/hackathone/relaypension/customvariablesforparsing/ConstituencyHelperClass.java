package com.codewarriors.hackathone.relaypension.customvariablesforparsing;

import java.util.ArrayList;

/**
 * Created by manpreet on 16/3/18.
 */

public class ConstituencyHelperClass {

    private int consAlimit=5;
    private int consBlimit=5;
    private int consClimit=5;

    public ArrayList<String> getConstituency()
    {
        ArrayList<String> constituencylist = new ArrayList<String>();
        constituencylist.add("A");
        constituencylist.add("B");
        constituencylist.add("C");

        return constituencylist;
    }

    public int getConsAlimit() {
        return consAlimit;
    }

    public int getConsBlimit() {
        return consBlimit;
    }

    public int getConsClimit() {
        return consClimit;
    }
}
