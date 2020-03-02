/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author bradharr
 */
public class city {
    //City Variables
    public int cityId;
    public String city;
    public String country;

    //City Constructor
    public city(int cityId, String city, String country) {
        this.cityId = cityId;
        this.city = city;
        this.country = country;
    }
    //City Getters & Setters
    public int getCityId() {
        return cityId;
    }

    public void setCityId(int cityId) {
        this.cityId = cityId;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }




}
