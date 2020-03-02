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
public class ApptRpt {
    
    //Appointment Report Variables
    private String month;
    private String type;
    private String total;

    //Appointment Report Getters & Setters
    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }
    //Appointment Report Constructor
    public ApptRpt(String month, String type, String total) {
        this.month = month;
        this.type = type;
        this.total = total;
    }
    
}
