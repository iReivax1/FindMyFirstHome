package com.example.findmyfirsthome.Boundary;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.findmyfirsthome.Controller.ProfileControl;
import com.example.findmyfirsthome.R;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;

public class ProfileUI extends AppCompatActivity implements View.OnFocusChangeListener {

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_ui); //Display UI
        Button next = (Button) findViewById(R.id.next); //For next button
        final RadioGroup radioGroupMS = (RadioGroup) findViewById(R.id.radioGroupMS);
        final RadioGroup radioGroupFTB = (RadioGroup) findViewById(R.id.radioGroupFTB);
        final RadioGroup radioGroupFTB2 = (RadioGroup) findViewById(R.id.radioGroupFTB2);
        final RadioGroup radioGroupCiti = (RadioGroup) findViewById(R.id.radioGroupCiti);
        final RadioGroup radioGroupCiti2 = (RadioGroup) findViewById(R.id.radioGroupCiti2);
        final EditText ageInput = (EditText) findViewById(R.id.ageInput);
        final EditText ageInput2 = (EditText) findViewById(R.id.ageInput2);
        final EditText grossMSalary = (EditText) findViewById(R.id.grossMSalary);
        final EditText grossMSalary2 = (EditText) findViewById(R.id.grossMSalary2);

        ageInput.setOnFocusChangeListener(this);
        ageInput2.setOnFocusChangeListener(this);
        grossMSalary.setOnFocusChangeListener(this);
        grossMSalary2.setOnFocusChangeListener(this);



        radioGroupMS.setOnCheckedChangeListener(new OnCheckedChangeListener() //poll for change in radio buttons
        {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // checkedId is the RadioButton selected
                if(checkedId==R.id.radioBtnSingle){ //Blur out Partner's input if user is single.
                    findViewById(R.id.ageInput2).setEnabled(false); //To disable inputs
                    findViewById(R.id.grossMSalary2).setEnabled(false);
                    for (int i = 0; i < radioGroupCiti2.getChildCount(); i++) {
                        radioGroupCiti2.getChildAt(i).setEnabled(false);
                    }
                    for (int i = 0; i < radioGroupFTB2.getChildCount(); i++) {
                        radioGroupFTB2.getChildAt(i).setEnabled(false);
                    }
                    ageInput2.setText(null); //set partner's field to empty.
                    grossMSalary2.setText(null);

                }
                else if(checkedId==R.id.radioBtnMarried){
                    findViewById(R.id.ageInput2).setEnabled(true); //To enable inputs
                    findViewById(R.id.grossMSalary2).setEnabled(true);
                    for (int i = 0; i < radioGroupCiti2.getChildCount(); i++) {
                        radioGroupCiti2.getChildAt(i).setEnabled(true);
                    }
                    for (int i = 0; i < radioGroupFTB2.getChildCount(); i++) {
                        radioGroupFTB2.getChildAt(i).setEnabled(true);
                    }
                }
            }
        });


        next.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                Intent i = new Intent( ProfileUI.this, ProfileUI2.class);
                i.putExtra ("radioGroupMSId",radioGroupMS.getId());
                i.putExtra ("radioGroupFTBId",radioGroupFTB.getId());
                i.putExtra ("radioGroupCitiId",radioGroupCiti.getId());
                i.putExtra ("ageInputStr",ageInput.getText().toString());
                i.putExtra ("grossMSalaryStr",grossMSalary.getText().toString());

                i.putExtra ("radioGroupFTB2Id",radioGroupFTB2.getId());
                i.putExtra ("radioGroupCiti2Id",radioGroupCiti2.getId());
                i.putExtra ("ageInputStr2",ageInput2.getText().toString());
                i.putExtra ("grossMSalaryStr2",grossMSalary2.getText().toString());
                startActivity(i);

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
