package com.angeles.steffany;

public class Person {

    private String FullName;
    private Integer Age;
    private String Gender;

    public Person(String FullName, Integer Age, String Gender){
        this.FullName = FullName;
        this.Age = Age;
        this.Gender = Gender;
    }


    public String getFullName() {
        return FullName;
    }

    public Integer getAge() {
        return Age;
    }

    public String getGender() {

        return Gender;
    }

}
