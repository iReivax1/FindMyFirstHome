package com.example.findmyfirsthome.Boundary;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.findmyfirsthome.Controller.CalculateProfileControl;
import com.example.findmyfirsthome.Controller.ProfileControl;
import com.example.findmyfirsthome.R;

public class ProfileUI3 extends AppCompatActivity implements View.OnFocusChangeListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_ui3);
        final AppCompatActivity activity = ProfileUI3.this;

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

        final String carLoanValueStr = getIntent().getStringExtra("carLoanValueStr");
        final String creditDebtValueStr = getIntent().getStringExtra("creditDebtValueStr");
        final String studyLoanValueStr = getIntent().getStringExtra("studyLoanValueStr");
        final String otherCommitsValueStr = getIntent().getStringExtra("otherCommitsValueStr");
        final String mainOAValueStr = getIntent().getStringExtra("mainOAValueStr");
        final String coOAValueStr = getIntent().getStringExtra("coOAValueStr");
        final String hMembersValueStr = getIntent().getStringExtra("hMembersValueStr");

        final int hMembersValue = Integer.parseInt(hMembersValueStr); //getting number of members
        final LinearLayout mainLayout=(LinearLayout)findViewById(R.id.profileUI3Linear); //setting main layout

        for(int i=0;i<hMembersValue;++i) { // creating layout programmatically depending on how many members
            // create a new textview
            TextView header = new TextView(this); //creating a textview for header
            // set header message
            int num = i+1;
            header.setText("Additional Household Member "+ num + ":"); //What the header will display
            // add the textview to the linearlayout
            mainLayout.addView(header); //include it into the layout

            LinearLayout horizontalLayout = new LinearLayout(this); //setting horizontal layout
            horizontalLayout.setOrientation(LinearLayout.HORIZONTAL);

            TextView reqSalary = new TextView(this); //for displaying "Gross Monthly Salary"
            reqSalary.setText("Gross Monthly Salary(SGD):");

            EditText hMemberSalary= new EditText(this); //Setting up input textbox
            hMemberSalary.setId(i);
            hMemberSalary.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_SIGNED); //set to request for positive integers only
            hMemberSalary.setBackgroundResource(R.drawable.textbox);
            hMemberSalary.setTextSize(14);
            hMemberSalary.setText("0");
            LinearLayout.LayoutParams Params1 = new LinearLayout.LayoutParams(220,100); //setting size of textbox
            hMemberSalary.setLayoutParams(Params1);

            LinearLayout.LayoutParams marginlayoutreqSalary = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            marginlayoutreqSalary.setMargins(0,0,100,0); //add margins
            reqSalary.setLayoutParams(marginlayoutreqSalary);

            horizontalLayout.addView(reqSalary); //include into horizontal layout
            horizontalLayout.addView(hMemberSalary); //include into horizontal layout
            mainLayout.addView(horizontalLayout); //include into main layout
        }


        Button submitBtn = new Button(this);
        submitBtn.setText("Submit");
        submitBtn.setTextColor(Color.parseColor("#FFFFFF"));
        submitBtn.setBackgroundColor(Color.parseColor("#2B3990"));

        LinearLayout.LayoutParams marginlayoutsubmitBtn = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        marginlayoutsubmitBtn.setMargins(750,50,0,0); //add margins to submit button
        submitBtn.setLayoutParams(marginlayoutsubmitBtn);
        mainLayout.addView(submitBtn); //add submit button

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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

                pc.setCarLoan(carLoanValueStr);
                pc.setCreditDebt(creditDebtValueStr);
                pc.setStudyLoan(studyLoanValueStr);
                pc.setOtherCommits(otherCommitsValueStr);
                pc.setMainOA(mainOAValueStr);
                pc.setCoOA(coOAValueStr);
                pc.setNoOfhMembers(hMembersValueStr);

                pc.setAllhMembers(hMembersValue,activity);
                pc.printSalaries();
                pc.writeProfile(activity);
                Log.d("test", "come here");

                CalculateProfileControl cpc = new CalculateProfileControl(activity);
                //Must be set in this order!
                cpc.setMaxMortgagePeriod();
                cpc.setMonthlyInstallment();
                cpc.setMaxMortgage();
                cpc.setMaxPropertyPrice();
                cpc.setDownpayment();
                cpc.setAffordability();
                cpc.writeCalculatedProfile(activity);

                Intent submitting = new Intent( ProfileUI3.this, HDBDevelopmentUI.class);
                startActivity(submitting);
            }
        });

    }
    @Override
    public void onFocusChange(View v, boolean hasFocus) { //set field to 0 if empty
        if(v instanceof EditText) {
           EditText x = (EditText) v; //downcasting
            if (!hasFocus) {
                // do something
                String value = x.getText().toString();
                if (value.isEmpty() == true) {
                    x.setText("0");
                }
            }
        }
    }
}
