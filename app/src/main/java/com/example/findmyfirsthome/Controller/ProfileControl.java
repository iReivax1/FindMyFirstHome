package com.example.findmyfirsthome.Controller;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.widget.EditText;

import com.example.findmyfirsthome.Entity.UserData;
import com.example.findmyfirsthome.R;

import java.util.ArrayList;

public class ProfileControl{
    private UserData ud = new UserData();

    public void setMaritalStatus(int marriedID){
        if(marriedID== R.id.radioBtnMarried){
            ud.setMarried(true);
        }
        else{
            ud.setMarried(false);
        }
    }

    public boolean getMaritalStatus(){return ud.isMarried();}

    public void setFirstTimeBuyer(int ftbID){
        if(ftbID== R.id.radioBtnYes){
            ud.setFirstTimeBuyer(true);
        }
        else {
            ud.setFirstTimeBuyer(false);
        }
    }
    public boolean getFirstTimeBuyer(){return ud.isFirstTimeBuyer(); }

    public void setCitizenship(int citiID){
        if(citiID== R.id.radioBtnSG){
            ud.setSingaporean(true);
        }
        else {
            ud.setSingaporean(false);
        }
    }
    public boolean getCitizenship(){return ud.isSingaporean();}

    public void setAge(String ageInputStr){
        if(ageInputStr.isEmpty()==true){
            ud.setAge(0);
        }
        else {
            int age = Integer.parseInt(ageInputStr);
            ud.setAge(age);
        }
    }
    public int getAge(){return ud.getAge();}

    public void setGrossMonthlySalary(String grossMSalaryStr){
        if (grossMSalaryStr.isEmpty()==true) {
            ud.setGrossSalary(0);
        }
        else {
            Float mSalary = Float.parseFloat(grossMSalaryStr);
            ud.setGrossSalary(mSalary);
        }
    }
    public double getGrossMonthlySalary(){return ud.getGrossSalary();}

    public void setFirstTimeBuyerPartner(int ftbPartnerID){
        if(ftbPartnerID== R.id.radioBtnYes2){
            ud.setFirstTimeBuyerPartner(true);
        }
        else {
            ud.setFirstTimeBuyerPartner(false);
        }
    }
    public boolean getFirstTimeBuyerPartner(){return ud.isFirstTimeBuyerPartner();}

    public void setCitizenshipPartner(int citiPartnerID){
        if(citiPartnerID== R.id.radioBtnSG2){
            ud.setSingaporeanPartner(true);
        }
        else {
            ud.setSingaporeanPartner(false);
        }
    }
    public boolean getCitizenshipPartner(){return ud.isSingaporeanPartner();}

    public void setAgePartner(String ageInput2Str){
        if(ageInput2Str.isEmpty()==true){
            ud.setAgePartner(0);
        }
        else {
            int agePartner = Integer.parseInt(ageInput2Str);
            ud.setAgePartner(agePartner);
        }
    }
    public int getAgePartner(){return ud.getAgePartner();}

    public void setGrossMonthlySalaryPartner(String grossMSalary2Str) {
        if (grossMSalary2Str.isEmpty()==true) {
            ud.setGrossSalaryPartner(0);
        }
        else {
            Float mSalaryPartner = Float.parseFloat(grossMSalary2Str);
            ud.setGrossSalaryPartner(mSalaryPartner);
        }
    }

    public double getGrossMonthlySalaryPartner(){return ud.getGrossSalaryPartner();}

    public void setCarLoan(String carLoanStr) {
        if (carLoanStr.isEmpty()==true) {
            ud.setCarLoan(0);
        }
        else {
            Float carLoanFloat = Float.parseFloat(carLoanStr);
            ud.setCarLoan(carLoanFloat);
        }
    }
    public double getCarLoan(){return ud.getCarLoan();}

    public void setCreditDebt(String creditDebtStr) {
        if(creditDebtStr.isEmpty()==true){
            ud.setCreditLoan(0);
        }
        else {
            Float creditDebtFloat = Float.parseFloat(creditDebtStr);
            ud.setCreditLoan(creditDebtFloat);
        }
    }

    public double getCreditDebt(){return ud.getCreditLoan();}

    public void setStudyLoan(String studyLoanStr) {
        if(studyLoanStr.isEmpty()==true){
            ud.setStudyLoan(0);
        }
        else {
            Float studyLoanFloat = Float.parseFloat(studyLoanStr);
            ud.setStudyLoan(studyLoanFloat);
        }
    }

    public double setStudyLoan(){return ud.getStudyLoan();}

    public void setOtherCommits(String otherCommitsStr) {
        if(otherCommitsStr.isEmpty()==true) {
            ud.setOtherCommitments(0);
        }
        else {
            Float otherCommitsFloat = Float.parseFloat(otherCommitsStr);
            ud.setOtherCommitments(otherCommitsFloat);
        }
    }

    public double getOtherCommits(){return ud.getOtherCommitments();}

    public void setMainOA(String mainOAStr) {
        if(mainOAStr.isEmpty()==true) {
            ud.setBuyer1CPF(0);
        }
        else {
            Float mainOAFloat = Float.parseFloat(mainOAStr);
            ud.setBuyer1CPF(mainOAFloat);
        }
    }

    public double getMainOA(){return ud.getBuyer1CPF();}

    public void setCoOA(String coOAStr) {
        if(coOAStr.isEmpty()==true) {
            ud.setBuyer2CPF(0);
        }
        else {
            Float coOAFloat = Float.parseFloat(coOAStr);
            ud.setBuyer2CPF(coOAFloat);
        }
    }

    public double getCOOA(){return ud.getBuyer2CPF();}

    public void setNoOfhMembers(String hMembersStr) {
        if(hMembersStr.equals(null)) ud.setNumberOfAdditionalHouseholdMembers(0);
        else {
            Integer hMembersInt = Integer.parseInt(hMembersStr);
            ud.setNumberOfAdditionalHouseholdMembers(hMembersInt);
        }
    }

    public int getNoofhMembers(){return ud.getNumberOfAdditionalHouseholdMembers();}

    public void setAllhMembers(int hMembersValue, Activity a){
        for(int i=0;i<hMembersValue;i++){
            EditText hMembers = a.findViewById(i);
            if(hMembers.getText().toString().isEmpty()==true) {
                ud.appendSalarytoSalaryList(0.0);

            }
            else{
                double individualSalary = Float.parseFloat(hMembers.getText().toString());
                ud.appendSalarytoSalaryList(individualSalary);
            }
        }
    }
    public ArrayList<Double> getAllMembers(){return ud.getMembersSalaryList();}

    public void printSalaries(){
        ArrayList<Double> list = ud.getMembersSalaryList();
        for (int i = 0; i < list.size(); i++) {
            Log.d("123",Double.toString(list.get(i)));
        }
    }

    public void writeProfile(Activity activity){
        DatabaseController db = DatabaseController.getInstance(activity.getApplicationContext());
        db.writeUserData(ud);
    }

    public void readProfile(Activity activity){
        DatabaseController db = DatabaseController.getInstance(activity.getApplicationContext());
        ud = db.readUserData();
    }
    public UserData getUD(){
        return ud;
    }

}
