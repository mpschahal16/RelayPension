package com.codewarriors.hackathone.relaypension.customvariablesforparsing;

/**
 * Created by manpreet on 31/3/18.
 */

public class ReportCustomVAR {
    private String aadharno,formno,messag;

    public ReportCustomVAR() {
    }

    public ReportCustomVAR(String aadharno, String formno, String messag) {
        this.aadharno = aadharno;
        this.formno = formno;
        this.messag = messag;
    }


    public String getAadharno() {
        return aadharno;
    }

    public String getFormno() {
        return formno;
    }

    public String getMessag() {
        return messag;
    }
}
