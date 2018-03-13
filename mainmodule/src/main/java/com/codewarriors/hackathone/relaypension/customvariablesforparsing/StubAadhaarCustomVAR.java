package com.codewarriors.hackathone.relaypension.customvariablesforparsing;

/**
 * Created by manpreet on 12/3/18.
 */

public class StubAadhaarCustomVAR {
    private String FirstName,MiddleName,LastName,age,Gender,Dateofbirth,PhoneNo,AadharNo,
    HoseNo1,StreetOrArea,Postalcode,City,State;

    public StubAadhaarCustomVAR() {
    }

    public StubAadhaarCustomVAR(String firstName, String middleName, String lastName,String age, String gender, String dateofbirth, String phoneNo, String aadharNo, String hoseNo1, String streetOrArea, String postalcode, String city, String state) {
        this.FirstName = firstName;
        this.MiddleName = middleName;
        this.LastName = lastName;
        this.age=age;
        this.Gender = gender;
        this.Dateofbirth = dateofbirth;
        this.PhoneNo = phoneNo;
        this.AadharNo = aadharNo;
        this.HoseNo1 = hoseNo1;
        this.StreetOrArea = streetOrArea;
        this.Postalcode = postalcode;
        this.City = city;
        this.State = state;
    }

    public String getFirstName() {
        return FirstName;
    }

    public String getAge() {
        return age;
    }

    public String getMiddleName() {
        return MiddleName;
    }

    public String getLastName() {
        return LastName;
    }

    public String getGender() {
        return Gender;
    }

    public String getDateofbirth() {
        return Dateofbirth;
    }

    public String getPhoneNo() {
        return PhoneNo;
    }

    public String getAadharNo() {
        return AadharNo;
    }

    public String getHoseNo1() {
        return HoseNo1;
    }

    public String getStreetOrArea() {
        return StreetOrArea;
    }

    public String getPostalcode() {
        return Postalcode;
    }

    public String getCity() {
        return City;
    }

    public String getState() {
        return State;
    }
}
