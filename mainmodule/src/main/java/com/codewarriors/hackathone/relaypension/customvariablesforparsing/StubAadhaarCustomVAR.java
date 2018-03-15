package com.codewarriors.hackathone.relaypension.customvariablesforparsing;

/**
 * Created by manpreet on 12/3/18.
 */

public class StubAadhaarCustomVAR {
    private String firstName,middleName,lastName,gender,dateofbirth,phoneNo,aadharNo,
    hoseno1,streetorarea,postalcode,city,state;

    public StubAadhaarCustomVAR() {
    }

    public StubAadhaarCustomVAR(String firstName, String middleName, String lastName,String gender, String dateofbirth, String phoneNo, String aadharNo, String hoseNo1, String streetOrArea, String postalcode, String city, String state) {
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.gender = gender;
        this.dateofbirth = dateofbirth;
        this.phoneNo = phoneNo;
        this.aadharNo = aadharNo;
        this.hoseno1 = hoseNo1;
        this.streetorarea = streetOrArea;
        this.postalcode = postalcode;
        this.city = city;
        this.state = state;
    }
    public String getFirstName() {return firstName;}

    public String getMiddleName() {return middleName;}

    public String getLastName() {return lastName;}

    public String getGender() {return gender;}

    public String getDateofbirth() {return dateofbirth;}

    public String getPhoneNo() {return phoneNo;}

    public String getAadharNo() {return aadharNo;}


    public String getHoseno1() {return hoseno1;}

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

}
