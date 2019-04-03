package com.example.findmyfirsthome.Model;

import java.util.ArrayList;

public class UserData {
    private boolean isMarried = true;
    private boolean isFirstTimeBuyer = true;
    private boolean isSingaporean = true; //if true is singaporean
    private int age =-1;
    private double grossSalary = 0;

    private boolean isFirstTimeBuyerPartner = true;
    private boolean isSingaporeanPartner = true; //if true is singaporean
    private int agePartner = -1;
    private double grossSalaryPartner = 0;

    private double carLoan = 0;
    private double creditLoan = 0;
    private double studyLoan = 0;
    private double otherCommitments = 0;

    private double buyer1CPF = 0;
    private double buyer2CPF = 0;

    public int numberOfAdditionalHouseholdMembers = 0;
    ArrayList<Double> membersSalaryList = new ArrayList<Double>();

    //empty constructor
    public UserData(){

    }

    //userdata constructor for couples, all filled
    public UserData(boolean isMarried, boolean isFirstTimeBuyer, boolean isSingaporean, int age, double grossSalary, boolean isFirstTimeBuyerPartner, boolean isSingaporeanPartner, int agePartner, double grossSalaryPartner,
                    double carLoan, double creditLoan, double studyLoan, double otherCommitments, double buyer1CPF, double buyer2CPF, int numberOfAdditionalHouseholdMembers, ArrayList<Double> membersSalaryList){
        //Compulsory
        this.isMarried = isMarried;
        this.isFirstTimeBuyer = isFirstTimeBuyer;
        this.isSingaporean = isSingaporean; //if true is singaporean
        this.age = age;
        this.grossSalary = grossSalary;
        this.buyer1CPF = buyer1CPF;

        //If isMarried = true
       this.isFirstTimeBuyerPartner = isFirstTimeBuyerPartner;
       this.isSingaporeanPartner = isSingaporeanPartner; //if true is singaporean
       this.agePartner = agePartner;
       this.grossSalaryPartner = grossSalaryPartner;
       this.buyer2CPF = buyer2CPF;

       //optional
       this.carLoan = carLoan;
       this.creditLoan = creditLoan;
       this.studyLoan = studyLoan;
       this.otherCommitments = otherCommitments;

       this.numberOfAdditionalHouseholdMembers = numberOfAdditionalHouseholdMembers;
       this.membersSalaryList = membersSalaryList;
    }

    //get Set functions

    public boolean isMarried() {
        return isMarried;
    }

    public void setMarried(boolean married) {
        isMarried = married;
    }

    public boolean isFirstTimeBuyer() {
        return isFirstTimeBuyer;
    }

    public void setFirstTimeBuyer(boolean firstTimeBuyer) {
        isFirstTimeBuyer = firstTimeBuyer;
    }

    public boolean isSingaporean() {
        return isSingaporean;
    }

    public void setSingaporean(boolean singaporean) {
        isSingaporean = singaporean;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public double getGrossSalary() {
        return grossSalary;
    }

    public void setGrossSalary(double grossSalary) {
        this.grossSalary = grossSalary;
    }

    public boolean isFirstTimeBuyerPartner() {
        return isFirstTimeBuyerPartner;
    }

    public void setFirstTimeBuyerPartner(boolean firstTimeBuyerPartner) {
        isFirstTimeBuyerPartner = firstTimeBuyerPartner;
    }

    public boolean isSingaporeanPartner() {
        return isSingaporeanPartner;
    }

    public void setSingaporeanPartner(boolean singaporeanPartner) {
        isSingaporeanPartner = singaporeanPartner;
    }

    public int getAgePartner() {
        return agePartner;
    }

    public void setAgePartner(int agePartner) {
        this.agePartner = agePartner;
    }

    public double getGrossSalaryPartner() {
        return grossSalaryPartner;
    }

    public void setGrossSalaryPartner(double grossSalaryPartner) {
        this.grossSalaryPartner = grossSalaryPartner;
    }

    public double getCarLoan() {
        return carLoan;
    }

    public void setCarLoan(double carLoan) {
        this.carLoan = carLoan;
    }

    public double getCreditLoan() {
        return creditLoan;
    }

    public void setCreditLoan(double creditLoan) {
        this.creditLoan = creditLoan;
    }

    public double getStudyLoan() {
        return studyLoan;
    }

    public void setStudyLoan(double studyLoan) {
        this.studyLoan = studyLoan;
    }

    public double getOtherCommitments() {
        return otherCommitments;
    }

    public void setOtherCommitments(double otherCommitments) {
        this.otherCommitments = otherCommitments;
    }

    public double getBuyer1CPF() {
        return buyer1CPF;
    }

    public void setBuyer1CPF(double buyer1CPF) {
        this.buyer1CPF = buyer1CPF;
    }

    public double getBuyer2CPF() {
        return buyer2CPF;
    }

    public void setBuyer2CPF(double buyer2CPF) {
        this.buyer2CPF = buyer2CPF;
    }

    public int getNumberOfAdditionalHouseholdMembers() {
        return numberOfAdditionalHouseholdMembers;
    }

    public void setNumberOfAdditionalHouseholdMembers(int numberOfAdditionalHouseholdMembers) {
        this.numberOfAdditionalHouseholdMembers = numberOfAdditionalHouseholdMembers;
    }

    public ArrayList<Double> getMembersSalaryList() {
        return membersSalaryList;
    }

    public void setMembersSalaryList(ArrayList<Double> membersSalaryList) {
        this.membersSalaryList = membersSalaryList;
    }
    public void appendSalarytoSalaryList(Double salaryHMember){
        membersSalaryList.add(salaryHMember);
    }
}


