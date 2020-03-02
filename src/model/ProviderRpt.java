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
public class ProviderRpt {
    //Provider Report Variables
    private String provider;
    private String start;
    private String end;
    private String customer;

    //Provider Report Getters & Setters
    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }
    //Provider Report Constructor
    public ProviderRpt(String provider, String start, String end, String customer) {
        this.provider = provider;
        this.start = start;
        this.end = end;
        this.customer = customer;
    }
    
}
