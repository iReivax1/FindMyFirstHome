package com.example.findmyfirsthome.Controller;
import com.example.findmyfirsthome.Entity.CalculatedProfile;
import com.example.findmyfirsthome.Entity.UserData;

/* TODO: Add Database methods to read and write
 * TODO: Retrieve AHG and SHG values
 *      avg_monthlyhouseholdincome = sum of all incomes/number of household members*/

public class CalculateProfileControl{
    final double annualIR = 0.026;
    final double monthIR = annualIR / 12;
    UserData udSaved;
    CalculatedProfile cpSaved;

    public CalculateProfileControl() {
        udSaved = new UserData();              //retrieved from database NOT NEW
        cpSaved = new CalculatedProfile();     //new

    }

    public void setMaxMortgage(){
        double loanPeriod = cpSaved.getMaxMortgagePeriod();
        double maxMortgage = (cpSaved.getMonthlyInstallment()) * (Math.pow((1+monthIR),(loanPeriod*12)) -1)/(monthIR * (Math.pow((1+monthIR),(loanPeriod*12))));
        cpSaved.setMaxMortgage(maxMortgage);
    }

    public void setMonthlyInstallment() {
        double financial_commitment = udSaved.getCarLoan() + udSaved.getCreditLoan() + udSaved.getStudyLoan() +  udSaved.getOtherCommitments();
        double total_income = udSaved.getGrossSalary()+udSaved.getGrossSalaryPartner();
        if (financial_commitment < 0.7 * total_income)
            cpSaved.setMonthlyInstallment(0.30 * (udSaved.getGrossSalary() + udSaved.getGrossSalaryPartner()));
        else
            cpSaved.setMonthlyInstallment(total_income-financial_commitment);
    }

    public void setMaxMortgagePeriod() {
        int age_mean;
        if(udSaved.getAgePartner() != 0)
            age_mean = (int)Math.ceil(((float)(udSaved.getAge() + udSaved.getAgePartner()))/2);
        else
            age_mean = udSaved.getAge();
        if (age_mean >= 21 && age_mean <= 54)
            cpSaved.setMaxMortgagePeriod(55 - age_mean);
        else
            cpSaved.setMaxMortgagePeriod(65 - age_mean);
    }

    public void setMaxPropertyPrice() {
        cpSaved.setMaxPropertyPrice(cpSaved.getMaxMortgage() * 100/90) ;
    }

    /* public double calculateMonthlyInstallment_Cash() {
         return DBSCalc.calculateMonthlyInstallment() - 0.23*(owner1_salary+owner2_salary);
     }*/
    public void setDownpayment() {
        cpSaved.setDownpayment(0.1 * cpSaved.getMaxPropertyPrice());
    }


}