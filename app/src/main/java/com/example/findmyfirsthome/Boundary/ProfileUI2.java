package com.example.findmyfirsthome.Boundary;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.findmyfirsthome.Controller.CalculateProfileControl;
import com.example.findmyfirsthome.Controller.ProfileControl;
import com.example.findmyfirsthome.R;

public class ProfileUI2 extends AppCompatActivity implements View.OnFocusChangeListener {
    double salary;
    double salary2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_ui2);
        final AppCompatActivity activity = ProfileUI2.this;
        final Intent intent = getIntent();


        final int radioGroupMScheckedID = getIntent().getIntExtra("radioGroupMScheckedID", 0);
        final int radioGroupFTBcheckedID = getIntent().getIntExtra("radioGroupFTBcheckedID", 0);
        final int radioGroupCiticheckedID = getIntent().getIntExtra("radioGroupCiticheckedID", 0);
        final String ageInputStr = getIntent().getStringExtra("ageInputStr");
        final String grossMSalaryStr = getIntent().getStringExtra("grossMSalaryStr");

        final int radioGroupFTB2checkedID = getIntent().getIntExtra("radioGroupFTB2checkedID", 0);
        final int radioGroupCiti2checkedID = getIntent().getIntExtra("radioGroupCiti2checkedID", 0);
        final String ageInputStr2 = getIntent().getStringExtra("ageInputStr2");
        final String grossMSalaryStr2 = getIntent().getStringExtra("grossMSalaryStr2");

        final EditText carLoan = (EditText) findViewById(R.id.carLoan);
        final EditText creditDebt = (EditText) findViewById(R.id.creditDebt);
        final EditText studyLoan = (EditText) findViewById(R.id.studyLoan);
        final EditText otherCommits = (EditText) findViewById(R.id.otherCommits);
        final EditText mainOA = (EditText) findViewById(R.id.mainOA);
        final EditText coOA = (EditText) findViewById(R.id.coOA);
        final EditText hMembers = (EditText) findViewById(R.id.hMembers);

        salary = Double.parseDouble(grossMSalaryStr);
        salary2 = Double.parseDouble(grossMSalaryStr2);

        carLoan.setOnFocusChangeListener(this); //To set field to 0 if empty
        creditDebt.setOnFocusChangeListener(this);
        studyLoan.setOnFocusChangeListener(this);
        otherCommits.setOnFocusChangeListener(this);
        mainOA.setOnFocusChangeListener(this);
        coOA.setOnFocusChangeListener(this);
        hMembers.setOnFocusChangeListener(this);

        ProfileControl pc = new ProfileControl();
        pc.readProfile(this);
        if(pc.getUD()!=null) {
            carLoan.setText(Double.toString(pc.getCarLoan()));
            creditDebt.setText(Double.toString(pc.getCreditDebt()));
            studyLoan.setText(Double.toString(pc.getStudyLoan()));
            otherCommits.setText(Double.toString(pc.getOtherCommits()));
            mainOA.setText(Double.toString(pc.getMainOA()));
            coOA.setText(Double.toString(pc.getCOOA()));
            hMembers.setText(Integer.toString(pc.getNoofhMembers()));
        }

        final Button skip = (Button) findViewById(R.id.skip);
        Button next2 = (Button) findViewById(R.id.next2);

        skip.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                //save all values into database
                final ProfileControl pc = new ProfileControl();
                pc.setMaritalStatus(radioGroupMScheckedID);
                pc.setFirstTimeBuyer(radioGroupFTBcheckedID);
                pc.setCitizenship(radioGroupCiticheckedID);
                pc.setAge(ageInputStr);
                pc.setGrossMonthlySalary(grossMSalaryStr);

                pc.setFirstTimeBuyerPartner(radioGroupFTB2checkedID);
                pc.setCitizenshipPartner(radioGroupCiti2checkedID);
                pc.setAgePartner(ageInputStr2);
                pc.setGrossMonthlySalaryPartner(grossMSalaryStr2);

                pc.setCarLoan(carLoan.getText().toString());
                pc.setCreditDebt(creditDebt.getText().toString());
                pc.setStudyLoan(studyLoan.getText().toString());
                pc.setOtherCommits(otherCommits.getText().toString());
                pc.setMainOA(mainOA.getText().toString());
                pc.setCoOA(coOA.getText().toString());
                pc.setNoOfhMembers(hMembers.getText().toString());
                pc.writeProfile(activity);
                CalculateProfileControl cpc = new CalculateProfileControl(activity);
                //Must be set in this order!
                cpc.setMaxMortgagePeriod();
                cpc.setMonthlyInstallment();
                cpc.setMaxMortgage();
                cpc.setMaxPropertyPrice();
                cpc.setDownpayment();
                cpc.setAffordability();
                cpc.writeCalculatedProfile(activity);
                Intent skip = new Intent( ProfileUI2.this, HDBDevelopmentUI.class);
                startActivity(skip);
            }
        });
        next2.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                int hMembersValue = Integer.parseInt(hMembers.getText().toString());

                if(hMembersValue==0){
                    //save all values into database
                    final ProfileControl pc = new ProfileControl();
                    pc.setMaritalStatus(radioGroupMScheckedID);
                    pc.setFirstTimeBuyer(radioGroupFTBcheckedID);
                    pc.setCitizenship(radioGroupCiticheckedID);
                    pc.setAge(ageInputStr);
                    pc.setGrossMonthlySalary(grossMSalaryStr);

                    pc.setFirstTimeBuyerPartner(radioGroupFTB2checkedID);
                    pc.setCitizenshipPartner(radioGroupCiti2checkedID);
                    pc.setAgePartner(ageInputStr2);
                    pc.setGrossMonthlySalaryPartner(grossMSalaryStr2);

                    pc.setCarLoan(carLoan.getText().toString());
                    pc.setCreditDebt(creditDebt.getText().toString());
                    pc.setStudyLoan(studyLoan.getText().toString());
                    pc.setOtherCommits(otherCommits.getText().toString());
                    pc.setMainOA(mainOA.getText().toString());
                    pc.setCoOA(coOA.getText().toString());
                    pc.setNoOfhMembers(hMembers.getText().toString());
                    pc.writeProfile(activity);
                    CalculateProfileControl cpc = new CalculateProfileControl(activity);
                    //Must be set in this order!
                    cpc.setMaxMortgagePeriod();
                    cpc.setMonthlyInstallment();
                    cpc.setMaxMortgage();
                    cpc.setMaxPropertyPrice();
                    cpc.setDownpayment();
                    cpc.setAffordability();
                    cpc.writeCalculatedProfile(activity);
                    Intent skip = new Intent( ProfileUI2.this, HDBDevelopmentUI.class);
                    startActivity(skip);
                }
                else {
                    Intent i = new Intent(ProfileUI2.this, ProfileUI3.class);
                    //putExtra all values
                    i.putExtra("radioGroupMScheckedID", radioGroupMScheckedID);
                    i.putExtra("radioGroupFTBcheckedID", radioGroupFTBcheckedID);
                    i.putExtra("radioGroupCiticheckedID", radioGroupCiticheckedID);
                    i.putExtra("ageInputStr", ageInputStr);
                    i.putExtra("grossMSalaryStr", grossMSalaryStr);

                    i.putExtra("radioGroupFTB2checkedID", radioGroupFTB2checkedID);
                    i.putExtra("radioGroupCiti2checkedID", radioGroupCiti2checkedID);
                    i.putExtra("ageInputStr2", ageInputStr2);
                    i.putExtra("grossMSalaryStr2", grossMSalaryStr2);

                    i.putExtra("carLoanValueStr", carLoan.getText().toString());
                    i.putExtra("creditDebtValueStr", creditDebt.getText().toString());
                    i.putExtra("studyLoanValueStr", studyLoan.getText().toString());
                    i.putExtra("otherCommitsValueStr", otherCommits.getText().toString());
                    i.putExtra("mainOAValueStr", mainOA.getText().toString());
                    i.putExtra("coOAValueStr", coOA.getText().toString());
                    i.putExtra("hMembersValueStr", hMembers.getText().toString());
                    startActivity(i);
                }

            }
        });

    }
    @Override
    public void onFocusChange(View v, boolean hasFocus) { //set field to 0 if empty
        if(v instanceof EditText) {
            EditText x = (EditText) v; //downcasting
            if (!hasFocus) {
                String value = x.getText().toString();
                if (value.isEmpty() == true) {
                    x.setText("0");
                }
                else if(Integer.parseInt(x.getText().toString())<0){
                    Toast.makeText(getApplicationContext(), "Commitments cannot be negative!", Toast.LENGTH_SHORT).show();
                    x.setText("0");
                }

                double grossTotal = salary+salary2;
                EditText carLoan = findViewById(R.id.carLoan);
                EditText creditDebt = findViewById(R.id.creditDebt);
                EditText studyLoan = findViewById(R.id.studyLoan);
                EditText otherCommits = findViewById(R.id.otherCommits);
                double totalCommits = Double.parseDouble(carLoan.getText().toString()) +Double.parseDouble(creditDebt.getText().toString())+Double.parseDouble(studyLoan.getText().toString())+Double.parseDouble(otherCommits.getText().toString());
                if(grossTotal< totalCommits){
                    Toast.makeText(getApplicationContext(), "total Commitments cannot be less than total Income!", Toast.LENGTH_SHORT).show();
                    x.setText("0");
                }

            }
        }
    }

    //called whenever an item in your options menu is selected
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            //R.id.home is the back button at the action bar which is at the top of the app
            case android.R.id.home:
                //make it such that action bar's back button which is actually a Up button acts like back button
                //Up button works by creating new task of the activity instead of actually back to previous activity
                //onBackPressed() called 1st so the original method of Up button will not be called
                onBackPressed();
                return true;
        }

        return(super.onOptionsItemSelected(item));
    }
}
