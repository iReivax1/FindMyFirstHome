package com.example.findmyfirsthome.Controller;

import com.example.findmyfirsthome.Entity.UserData;

import java.util.HashMap;

public class CalculateProfileControl{
    UserData udSaved = new UserData();              //retrieved from database NOT NEW
    private final double annualIR = 0.026;
    private final double monthIR = annualIR/12;

    public double getMaxLoan(){
        int loanPeriod = getMaxMortgagePeriod();
        return (getMonthlyInstallment()) * (Math.pow((1+monthIR),(loanPeriod*12)) -1)/(monthIR * (Math.pow((1+monthIR),(loanPeriod*12))));
    }

    public double getMonthlyInstallment() {
        double financial_commitment = udSaved.getCarLoan() + udSaved.getCreditLoan() + udSaved.getStudyLoan() +  udSaved.getOtherCommitments();
        double total_income = udSaved.getGrossSalary()+udSaved.getGrossSalaryPartner();
        if (financial_commitment < 0.7 * total_income)
            return 0.30 * (udSaved.getGrossSalary() + udSaved.getGrossSalaryPartner());
        else
            return (total_income-financial_commitment);
    }

    public int getMaxMortgagePeriod() {
        int age_mean;
        if(udSaved.getAgePartner() != 0)
            age_mean = (int)Math.ceil(((float)(udSaved.getAge() + udSaved.getAgePartner()))/2);
        else
            age_mean = udSaved.getAge();
        if (age_mean >= 21 && age_mean <= 54)
            return (55 - age_mean);
        else
            return (65 - age_mean);
    }

    public double getMaxPropertyPrice() {
        return getMaxLoan() * 100/90 ;
    }

    /* public double calculateMonthlyInstallment_Cash() {
         return DBSCalc.calculateMonthlyInstallment() - 0.23*(owner1_salary+owner2_salary);
     }*/



    public double calculateDownpayment() {
        return 0.1 * getMaxPropertyPrice();
    }
}
