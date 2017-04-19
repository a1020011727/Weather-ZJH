package com.example.dva.bean;

/**
 * Created by D.va on 2017/4/19.
 */

public class City {
    private String province;
    private String city;
    private String number;
    private String firstPY;
    private String allPY;
    private String allFirstPY;

    public String getProvince() {
        return province;
    }

    public String getAllFirstPY() {
        return allFirstPY;
    }

    public String getAllPY() {
        return allPY;
    }

    public String getCity() {
        return city;
    }

    public String getFirstPY() {
        return firstPY;
    }

    public String getNumber() {
        return number;
    }



    public City(String province, String city, String number, String firstPY, String allPY , String allFirstPY) {
        this.province = province;
        this.city = city;
        this.number = number;
        this.firstPY = firstPY;
        this.allPY = allPY;
        this.allFirstPY = allFirstPY;
    }

}
