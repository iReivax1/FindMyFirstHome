package com.example.findmyfirsthome.Entity;

import java.util.ArrayList;

public class UserData {
    private boolean isMarried;
    private boolean isFirstTimeBuyer;
    private boolean isSingaporean; //if true is singaporean
    private int age;
    private double grossSalary = 0;

    private double carLoan = 0;
    private double creditLoan = 0;
    private double studyLoan = 0;
    private double otherCommittment = 0;

    private double buyer1CPF;
    private double buyer2CPF;

    public int numberOfAdditionalHouseholdMemeber;
    ArrayList<Double> membersSalaryList = new ArrayList<Double>();

    //isMarried get set
    public boolean getIsMarried()
    {
        return this.isMarried;
    }

    public void setIsMarried(boolean isMarried)
    {
        this.isMarried = isMarried;
    }

    //isFirstTimeBuyer get set
    public boolean getIsFirstTimeBuyer()
    {
        return this.isFirstTimeBuyer;
    }

    public void setIsFirstTimeBuyer(boolean isFirstTimeBuyer)
    {
        this.isFirstTimeBuyer = isFirstTimeBuyer;
    }

    //isSingaporean get set
    public boolean getIsSingaporean()
    {
        return this.isSingaporean;
    }

    public void setIsSingaporean(boolean isSingaporean)
    {
        this.isSingaporean = isSingaporean;
    }

    //age get set
    public int getAge()
    {
        return this.age;
    }

    public void setAge(int age)
    {
        this.age = age;
    }

    //grossSalary get set
    public double getGrossSalary()
    {
        return this.grossSalary;
    }

    public void setGrossSalary(double grossSalary)
    {
        this.grossSalary = grossSalary;
    }

    //carLoan get set
    public double getCarLoan()
    {
        return this.carLoan;
    }

    public void setCarLoan(double carLoan)
    {
        this.carLoan = carLoan;
    }

    //creditLoan get set
    public double getCreditLoan()
    {
        return this.creditLoan;
    }

    public void setCreditLoan(double creditLoan)
    {
        this.creditLoan = creditLoan;
    }

    //studyLoan get set
    public double getStudyLoan()
    {
        return this.studyLoan;
    }

    public void setStudyLoan(double studyLoan)
    {
        this.studyLoan = studyLoan;
    }

    //otherCommittment get set
    public double getOtherCommittment()
    {
        return this.otherCommittment;
    }

    public void setOtherCommittment(double otherCommittment)
    {
        this.otherCommittment = otherCommittment;
    }

    //buyer1CPF get set
    public double getBuyer1CPF()
    {
        return this.buyer1CPF;
    }

    public void setBuyer1CPF(double buyer1CPF)
    {
        this.buyer1CPF = buyer1CPF;
    }

    //buyer2CPF get set
    public double getBuyer2CPF()
    {
        return this.buyer2CPF;
    }

    public void setBuyer2CPF(double buyer2CPF)
    {
        this.buyer2CPF = buyer2CPF;
    }

    //numberOfAdditionalHouseholdMemeber get set
    public int getNumberOfAdditionalHouseholdMemeber()
    {
        return this.numberOfAdditionalHouseholdMemeber;
    }

    public void setNumberOfAdditionalHouseholdMemeber(int numberOfAdditionalHouseholdMemeber)
    {
        this.numberOfAdditionalHouseholdMemeber = numberOfAdditionalHouseholdMemeber;
    }

    //membersSalaryList get set
    public ArrayList<Double> getMembersSalaryList()
    {
        return this.membersSalaryList;
    }

    public void setMembersSalaryList(ArrayList<Double> membersSalaryList)
    {
        this.membersSalaryList = membersSalaryList;
    }
}
