package com.codewarriors.hackathone.relaypension.customvariablesforparsing;

/**
 * Created by manpreet on 19/3/18.
 */

public class FormPushPullCustomVAR {
    private String formno,constituency,firstName,middleName,lastName,age,gender,dateofbirth,phoneNo,aadharNo,
            hoseno1,streetorarea,postalcode,city,state,bankaccountno,bankname,familyincome;


    public FormPushPullCustomVAR() {
    }

    public FormPushPullCustomVAR(String formno, String constituency, String firstName, String middleName, String lastName, String age, String gender, String dateofbirth, String phoneNo,
                                 String aadharNo, String hoseno1, String streetorarea, String postalcode, String city, String state, String bankaccountno, String bankname,
                                 String familyincome) {
        this.formno = formno;
        this.constituency = constituency;
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.age = age;
        this.gender = gender;
        this.dateofbirth = dateofbirth;
        this.phoneNo = phoneNo;
        this.aadharNo = aadharNo;
        this.hoseno1 = hoseno1;
        this.streetorarea = streetorarea;
        this.postalcode = postalcode;
        this.city = city;
        this.state = state;
        this.bankaccountno = bankaccountno;
        this.bankname = bankname;
        this.familyincome = familyincome;
    }

    public String getFormno() {
        return formno;
    }

    public String getConstituency() {
        return constituency;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getAge() {
        return age;
    }

    public String getGender() {
        return gender;
    }

    public String getDateofbirth() {
        return dateofbirth;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public String getAadharNo() {
        return aadharNo;
    }

    public String getHoseno1() {
        return hoseno1;
    }

    public String getStreetorarea() {
        return streetorarea;
    }

    public String getPostalcode() {
        return postalcode;
    }

    public String getCity() {
        return city;
    }

    public String getState() {
        return state;
    }

    public String getBankaccountno() {
        return bankaccountno;
    }

    public String getBankname() {
        return bankname;
    }

    public String getFamilyincome() {
        return familyincome;
    }
}
