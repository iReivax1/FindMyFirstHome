/*
    1. Grants not completed.
    2. getCalProfileDetails() not completed.
*/
package com.example.findmyfirsthome.Model;

public class CalculatedProfile {
    //Get from database the exact grant amount
    private double AHG;
    private double SHG;

    private double maxMortgage;
    private double monthlyInstallment;
    private double maxMortgagePeriod;
    private double maxPropertyPrice;
    private double downpayment;
    public CalculatedProfile(){
        AHG=0;
        SHG=0;
        maxMortgage=0;
        monthlyInstallment=0;
        maxMortgagePeriod=0;
        maxPropertyPrice=0;
        downpayment=0;
    }


    public CalculatedProfile(double AHG, double SHG, double maxMortgage, double monthlyInstallment, double maxMortgagePeriod, double maxPropertyPrice, double downpayment){
        this.AHG=AHG;
        this.SHG=SHG;
        this.maxMortgage=maxMortgage;
        this.monthlyInstallment=monthlyInstallment;
        this.maxMortgagePeriod=maxMortgagePeriod;
        this.maxPropertyPrice=maxPropertyPrice;
        this.downpayment=downpayment;
    }

    public double getAHG() {
        return AHG;
    }

    public void setAHG(double AHG) {
        this.AHG = AHG;
    }

    public double getSHG() {
        return SHG;
    }

    public void setSHG(double SHG) {
        this.SHG = SHG;
    }

    public double getMaxMortgage() {
        return maxMortgage;
    }

    public void setMaxMortgage(double maxMortgage) {
        this.maxMortgage = maxMortgage;
    }

    public double getMonthlyInstallment() {
        return monthlyInstallment;
    }

    public void setMonthlyInstallment(double monthlyInstallment) {
        this.monthlyInstallment = monthlyInstallment;
    }

    public double getMaxMortgagePeriod() {
        return maxMortgagePeriod;
    }

    public void setMaxMortgagePeriod(double maxMortgagePeriod) {
        this.maxMortgagePeriod = maxMortgagePeriod;
    }

    public double getMaxPropertyPrice() {
        return maxPropertyPrice;
    }

    public void setMaxPropertyPrice(double maxPropertyPrice) {
        this.maxPropertyPrice = maxPropertyPrice;
    }

    public double getDownpayment() {
        return downpayment;
    }

    public void setDownpayment(double downpayment) {
        this.downpayment = downpayment;
    }

}
