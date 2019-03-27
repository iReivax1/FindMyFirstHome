package com.example.findmyfirsthome.Boundary;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.findmyfirsthome.Controller.ProfileControl;
import com.example.findmyfirsthome.R;

public class ProfileUI2 extends AppCompatActivity implements View.OnFocusChangeListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_ui2);
        final AppCompatActivity activity = ProfileUI2.this;
        final Intent intent = getIntent();
        final int radioGroupMSId = getIntent().getIntExtra("radioGroupMSId", 0);
        final int radioGroupFTBId = getIntent().getIntExtra("radioGroupFTBId", 0);
        final int radioGroupCitiId = getIntent().getIntExtra("radioGroupCitiId", 0);
        final String ageInputStr = getIntent().getStringExtra("ageInputStr");
        final String grossMSalaryStr = getIntent().getStringExtra("grossMSalaryStr");

        final int radioGroupFTB2Id = getIntent().getIntExtra("radioGroupFTB2Id", 0);
        final int radioGroupCiti2Id = getIntent().getIntExtra("radioGroupCiti2Id", 0);
        final String ageInputStr2 = getIntent().getStringExtra("ageInputStr2");
        final String grossMSalaryStr2 = getIntent().getStringExtra("grossMSalaryStr2");

        final EditText carLoan = (EditText) findViewById(R.id.carLoan);
        final EditText creditDebt = (EditText) findViewById(R.id.creditDebt);
        final EditText studyLoan = (EditText) findViewById(R.id.studyLoan);
        final EditText otherCommits = (EditText) findViewById(R.id.otherCommits);
        final EditText mainOA = (EditText) findViewById(R.id.mainOA);
        final EditText coOA = (EditText) findViewById(R.id.coOA);
        final EditText hMembers = (EditText) findViewById(R.id.hMembers);

        carLoan.setOnFocusChangeListener(this); //To set field to 0 if empty
        creditDebt.setOnFocusChangeListener(this);
        studyLoan.setOnFocusChangeListener(this);
        otherCommits.setOnFocusChangeListener(this);
        mainOA.setOnFocusChangeListener(this);
        coOA.setOnFocusChangeListener(this);
        hMembers.setOnFocusChangeListener(this);

        final Button skip = (Button) findViewById(R.id.skip);
        Button next2 = (Button) findViewById(R.id.next2);

        skip.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                //save all values into database
                final ProfileControl pc = new ProfileControl();
                pc.setMaritalStatus(radioGroupMSId);
                pc.setFirstTimeBuyer(radioGroupFTBId);
                pc.setCitizenship(radioGroupCitiId);
                pc.setAge(ageInputStr);
                pc.setGrossMonthlySalary(grossMSalaryStr);

                pc.setFirstTimeBuyerPartner(radioGroupFTB2Id);
                pc.setCitizenshipPartner(radioGroupCiti2Id);
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
                Intent skip = new Intent( ProfileUI2.this, HDBDevelopmentUI.class);
                startActivity(skip);
            }
        });
        next2.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                int hMembersValue = Integer.parseInt(hMembers.getText().toString());
                if(hMembersValue==0){
                    skip.setOnClickListener(this);
                }
                else {
                    Intent i = new Intent(ProfileUI2.this, ProfileUI3.class);
                    //putExtra all values
                    i.putExtra("radioGroupMSId", radioGroupMSId);
                    i.putExtra("radioGroupFTBId", radioGroupFTBId);
                    i.putExtra("radioGroupCitiId", radioGroupCitiId);
                    i.putExtra("ageInputStr", ageInputStr);
                    i.putExtra("grossMSalaryStr", grossMSalaryStr);

                    i.putExtra("radioGroupFTB2Id", radioGroupFTB2Id);
                    i.putExtra("radioGroupCiti2Id", radioGroupCiti2Id);
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
                // do something
                String value = x.getText().toString();
                if (value.isEmpty() == true) {
                    x.setText("0");
                }
            }
        }
    }
}
