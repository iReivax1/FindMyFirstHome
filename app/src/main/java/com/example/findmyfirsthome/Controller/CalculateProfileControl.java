package com.example.findmyfirsthome.Controller;

import android.app.Activity;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.findmyfirsthome.Entity.CalculatedProfile;
import com.example.findmyfirsthome.Entity.HDBDevelopment;
import com.example.findmyfirsthome.Entity.UserData;

import java.util.ArrayList;
import java.util.HashMap;


public class CalculateProfileControl{
    final double annualIR = 0.026;
    final double monthIR = annualIR / 12;
    UserData udSaved;
    CalculatedProfile cp;
    DatabaseController dbControl;
    ArrayList<HDBDevelopment> hdbDevelopments;

    public CalculateProfileControl(Activity activity) {
        dbControl = DatabaseController.getInstance(activity.getApplicationContext());
        udSaved = dbControl.readUserData();              //retrieved from database NOT NEW
        hdbDevelopments = dbControl.readHDBData();        //returns ArrayList<HDBDevelopment>
        cp = new CalculatedProfile();     //new


    }
    public void setAffordability(){
        dbControl.deleteHDBData(); //clear content in database

        for(int i=0;i< hdbDevelopments.size();i++){
            ArrayList<HashMap<String, Object>> readHDBFlatType = hdbDevelopments.get(i).getHDBFlatTypeDetailsList(); //get flat types of each development
            int numOfTrue = 0;
            for(HashMap<String, Object> j : readHDBFlatType){
                for(String k : j.keySet())
                {
                    if(k.contains("price")){
                        double price = (double) j.get(k);
                        if(price<cp.getMaxPropertyPrice()){ //set affordability to true if the property is < maximum property price allowed with the loan
                            j.put("affordability", true);
                            numOfTrue++;
                        }
                        else {
                            j.put("affordability", false);
                        }
                    }
                }

            }
            //true if at least one affordable flat type then set hdb development to true too
            if(numOfTrue>0) dbControl.writeHDBData(hdbDevelopments.get(i).getDevelopmentName(),hdbDevelopments.get(i).getDevelopmentDescription(),hdbDevelopments.get(i).getImgUrl(),true, hdbDevelopments.get(i).getCoordinates().latitude, hdbDevelopments.get(i).getCoordinates().longitude);
            else dbControl.writeHDBData(hdbDevelopments.get(i).getDevelopmentName(),hdbDevelopments.get(i).getDevelopmentDescription(),hdbDevelopments.get(i).getImgUrl(),false, hdbDevelopments.get(i).getCoordinates().latitude, hdbDevelopments.get(i).getCoordinates().longitude);
            for(int m=0;m<readHDBFlatType.size();m++) {
                dbControl.writeHDBFlatTypeData(hdbDevelopments.get(i).getDevelopmentName(), readHDBFlatType.get(m));
            }
        }

    }

    public void setMaxMortgage(){
        double loanPeriod = cp.getMaxMortgagePeriod();
        double maxMortgage = (cp.getMonthlyInstallment()) * (Math.pow((1+monthIR),(loanPeriod*12)) -1)/(monthIR * (Math.pow((1+monthIR),(loanPeriod*12))));
        Log.d("WHY", Double.toString(maxMortgage));
        cp.setMaxMortgage(maxMortgage);
    }

    public void setMonthlyInstallment() {
        double financial_commitment = udSaved.getCarLoan() + udSaved.getCreditLoan() + udSaved.getStudyLoan() +  udSaved.getOtherCommitments();
        double total_income = udSaved.getGrossSalary()+udSaved.getGrossSalaryPartner();
        if (financial_commitment < 0.7 * total_income)
            cp.setMonthlyInstallment(0.30 * (udSaved.getGrossSalary() + udSaved.getGrossSalaryPartner()));
        else
            cp.setMonthlyInstallment(total_income-financial_commitment);
    }

    public void setMaxMortgagePeriod() {
        int age_mean;
        if(udSaved.getAgePartner() != 0)
            age_mean = (int)Math.ceil(((float)(udSaved.getAge() + udSaved.getAgePartner()))/2);
        else
            age_mean = udSaved.getAge();
        if (age_mean >= 21 && age_mean <= 54)
            cp.setMaxMortgagePeriod(55 - age_mean);
        else
            cp.setMaxMortgagePeriod(65 - age_mean);
    }

    public void setMaxPropertyPrice() {
        setGrants();
        cp.setMaxPropertyPrice(cp.getMaxMortgage() * 100/90 + cp.getAHG()+cp.getSHG()) ;
    }

    /* public double calculateMonthlyInstallment_Cash() {
         return DBSCalc.calculateMonthlyInstallment() - 0.23*(owner1_salary+owner2_salary);
     }*/
    public void setDownpayment() {
        cp.setDownpayment(0.1 * cp.getMaxPropertyPrice());
    }

    public void setGrants() {
        double avg_householdincome = calculateAvgHouseholdIncome();
        if (avg_householdincome >= 8501) {
            cp.setAHG(0);
            cp.setSHG(0);
        }
        else {
            HashMap<String, Double> HDBGrantData = new HashMap<>();
            if (avg_householdincome <= 1501) {
                HDBGrantData = dbControl.readHDBGrantData("Up to $1500");

            } else if (avg_householdincome >= 1501 && avg_householdincome <= 2000) {
                HDBGrantData = dbControl.readHDBGrantData("$1,501 to 2,000");

            } else if (avg_householdincome >= 2001 && avg_householdincome <= 2500) {
                HDBGrantData = dbControl.readHDBGrantData("$2,001 to 2,500");

            } else if (avg_householdincome >= 25001 && avg_householdincome <= 3000) {
                HDBGrantData = dbControl.readHDBGrantData("$2,501 to 3,000");

            } else if (avg_householdincome >= 3001 && avg_householdincome <= 3500) {
                HDBGrantData = dbControl.readHDBGrantData("$3,001 to 3,500");

            } else if (avg_householdincome >= 3501 && avg_householdincome <= 4000) {
                HDBGrantData = dbControl.readHDBGrantData("$3,501 to 4,000");

            } else if (avg_householdincome >= 4001 && avg_householdincome <= 4500) {
                HDBGrantData = dbControl.readHDBGrantData("$4,001 to 4,500");

            } else if (avg_householdincome >= 4501 && avg_householdincome <= 5000) {
                HDBGrantData = dbControl.readHDBGrantData("$4,501 to 5,000");

            } else if (avg_householdincome >= 5001 && avg_householdincome <= 5500) {
                HDBGrantData = dbControl.readHDBGrantData("$5,001 to 5,500");

            } else if (avg_householdincome >= 5501 && avg_householdincome <= 6000) {
                HDBGrantData = dbControl.readHDBGrantData("$5,501 to 6,000");

            } else if (avg_householdincome >= 6001 && avg_householdincome <= 6500) {
                HDBGrantData = dbControl.readHDBGrantData("$6,001 to 5,500");

            } else if (avg_householdincome >= 6501 && avg_householdincome <= 7000) {
                HDBGrantData = dbControl.readHDBGrantData("$4,001 to 4,500");

            } else if (avg_householdincome >= 7001 && avg_householdincome <= 7500) {
                HDBGrantData = dbControl.readHDBGrantData("$7,001 to 7,500");

            } else if (avg_householdincome >= 7501 && avg_householdincome <= 8000) {
                HDBGrantData = dbControl.readHDBGrantData("$7,501 to 8,000");

            } else if (avg_householdincome >= 8001 && avg_householdincome <= 8500) {
                HDBGrantData = dbControl.readHDBGrantData("$8,001 to 8,500");

            }

            if(HDBGrantData.size() != 0) {
                cp.setAHG(HDBGrantData.get("AHG"));
                cp.setSHG(HDBGrantData.get("SHG"));
            }
            else
            {
                //when the person cannot afford any grant
                cp.setAHG(0);
                cp.setSHG(0);
            }
        }
    }

    public double calculateAvgHouseholdIncome() {
        double sum;
        int nOofmembers;
        if (udSaved.isMarried()) {
            sum = udSaved.getGrossSalary() + udSaved.getGrossSalaryPartner();
            nOofmembers = 2;
        } else {
            sum = udSaved.getGrossSalary();
            nOofmembers = 1;
        }
        for (int i = 0; i < udSaved.getMembersSalaryList().size(); i++) {
            sum += udSaved.getMembersSalaryList().get(i);
        }
        nOofmembers += udSaved.getNumberOfAdditionalHouseholdMembers();
        return sum / nOofmembers;

    }

    public void writeCalculatedProfile(Activity activity){
        DatabaseController db = DatabaseController.getInstance(activity.getApplicationContext());
        db.writeCalculatedProfile(cp);
    }


}
