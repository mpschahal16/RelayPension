package com.codewarriors.hackathone.relaypension.customvariablesforparsing;

/**
 * Created by manpreet on 19/3/18.
 */

public class ConsituencyCustomVAR {
    String username,uresid,aadharno,consituency,applictionstate;

    public ConsituencyCustomVAR() {
    }

    public ConsituencyCustomVAR(String username,String uresid, String aadharno, String consituency, String applictionstate) {
        this.username=username;
        this.uresid = uresid;
        this.aadharno = aadharno;
        this.consituency = consituency;
        this.applictionstate = applictionstate;
    }

    public String getUsername() {
        return username;
    }

    public String getUresid() {
        return uresid;
    }

    public String getAadharno() {
        return aadharno;
    }

    public String getConsituency() {
        return consituency;
    }

    public String getApplictionstate() {
        return applictionstate;
    }
}
