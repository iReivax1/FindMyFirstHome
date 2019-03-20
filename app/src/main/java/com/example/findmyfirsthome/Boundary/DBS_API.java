package com.example.findmyfirsthome.Boundary;

import static java.lang.Math.*;

public class DBS_API {

    private double owner1_salary, owner2_salary;
    private int owner1_age = 0, owner2_age = 0;
    private int loanperiod;
    private double annualIR = 0.026;
    private double monthIR = annualIR/12;

    public DBS_API(double owner1_salary, int owner1_age){
        this.owner1_salary = owner1_salary;
        this.owner1_age = owner1_age;
    }
    public DBS_API(double owner1_salary, double owner2_salary, int owner1_age, int owner2_age) {
        this.owner1_salary = owner1_salary;
        this.owner2_salary = owner2_salary;
        this.owner1_age = owner1_age;
        this.owner2_age = owner2_age;
    }
    public void calculateLoanperiod() {
        int age_mean = (owner1_age + owner2_age)/2;
        if (age_mean >= 21 && age_mean <= 54)
            this.loanperiod = 55 - age_mean;
        else
            this.loanperiod = 65 - age_mean;
    }

    public double calculateMonthlyInstallment() {
        return 0.25*(owner1_salary+owner2_salary);
    }

    public double calculateMaxLoan() {
        calculateLoanperiod();
        return calculateMonthlyInstallment()*(pow((1+monthIR),(loanperiod*12)) -1)/monthIR*(pow((1+monthIR),(loanperiod*12)));
    }
}
