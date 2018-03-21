package com.codewarriors.hackathone.relaypension.customvariablesforparsing;

import android.util.Log;

import java.util.ArrayList;

/**
 * Created by manpreet on 16/3/18.
 */

public class ConstituencyHelperClass {

    private int consAlimit=3;
    private int consBlimit=2;
    private int consClimit=4;

    public ArrayList<String> getConstituency()
    {
        ArrayList<String> constituencylist = new ArrayList<String>();
        constituencylist.add("A");
        constituencylist.add("B");
        constituencylist.add("C");

        return constituencylist;
    }


    public int getlimit(String constiuencyvar)
    {

        switch (constiuencyvar)
        {
            case "A":
            {
                return consAlimit;
            }
            case "B":
            {
                return consBlimit;
            }
            case "C":
            {
                return consClimit;
            }
            default:
            {
                Log.d("conslimit","default part");
            }


        }
        return 0;
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
