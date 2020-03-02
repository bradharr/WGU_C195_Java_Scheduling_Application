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
public class CustRpt {
    //Customer Report Variables
    private String customer;
    private String start;
    private String end;

    //Customer Report Constructor
    public CustRpt(String customer, String start, String end) {
        this.customer = customer;
        this.start = start;
        this.end = end;
    }
    //Customer Report Getters & Setters
    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
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
    
    
    
}
