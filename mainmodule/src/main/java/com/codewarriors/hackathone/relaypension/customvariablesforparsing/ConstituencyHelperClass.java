package com.codewarriors.hackathone.relaypension.customvariablesforparsing;

import java.util.ArrayList;

/**
 * Created by manpreet on 16/3/18.
 */

public class ConstituencyHelperClass {

    public ArrayList<String> getConstituency()
    {
        ArrayList<String> constituencylist = new ArrayList<String>();
        constituencylist.add("A");
        constituencylist.add("B");
        constituencylist.add("C");

        return constituencylist;
    }

    public ArrayList<Integer> getConstituencylimit()
    {
        ArrayList<Integer> maxvaluelist = new ArrayList<Integer>();
        maxvaluelist.add(5);
        maxvaluelist.add(5);
        maxvaluelist.add(5);

        return maxvaluelist;
    }
}
