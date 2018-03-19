package com.codewarriors.hackathone.relaypension.customvariablesforparsing;

/**
 * Created by manpreet on 19/3/18.
 */

public class ApplicationFormStateCustomVAR {
    String userid,consituency,aadharno,applivationsyaye;

    public ApplicationFormStateCustomVAR() {
    }

    public ApplicationFormStateCustomVAR(String userid, String consituency, String aadharno, String applivationsyaye) {
        this.userid = userid;
        this.consituency = consituency;
        this.aadharno = aadharno;
        this.applivationsyaye = applivationsyaye;
    }

    public String getUserid() {
        return userid;
    }

    public String getConsituency() {
        return consituency;
    }

    public String getAadharno() {
        return aadharno;
    }

    public String getApplivationsyaye() {
        return applivationsyaye;
    }
}
