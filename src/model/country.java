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
public class country {
    //Country Variables
    public int countryId;
    public String country;
    
    //Country Constructor
    public country(int countryId, String country) {
        this.countryId = countryId;
        this.country = country;
    }
    //Country Getters & Setters
    public int getCountryId() {
        return countryId;
    }

    public void setCountryId(int countryId) {
        this.countryId = countryId;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }


    
}
