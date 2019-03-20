package com.example.findmyfirsthome.Controller;

import com.example.findmyfirsthome.Boundary.DBS_API;

public class CalculateProfileControl{
    DBS_API DBSCalc;

    private double owner1_salary, owner2_salary; //values should be retrieved from database
    private int owner1_age, owner2_age;

    public CalculateProfileControl(double owner1_salary, double owner2_salary, int owner1_age, int owner2_age) {
        this.owner1_salary = owner1_salary;
        this.owner2_salary = owner2_salary;
        this.owner1_age = owner1_age;
        this.owner2_age= owner2_age;
        DBSCalc = new DBS_API(owner1_salary, owner2_salary, owner1_age, owner2_age);
    }

    public double calculateMaxPropertyPrice() {
        return DBSCalc.calculateMaxLoan() * 100/90 ;
    }

    public double calculateMonthlyInstallment_Cash() {
        return DBSCalc.calculateMonthlyInstallment() - 0.23*(owner1_salary+owner2_salary);
    }
    public double calculateDownpayment() {
        return 0.1 * calculateMaxPropertyPrice();
    }
}
