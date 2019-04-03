package com.example.findmyfirsthome.View;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.findmyfirsthome.Presenter.ProfileControl;
import com.example.findmyfirsthome.R;
import android.content.Intent;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Toast;

public class ProfileUI extends AppCompatActivity implements View.OnFocusChangeListener {
    private double totalSalary;
    private double salary1;
    private double salary2;

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

        ProfileControl pc = new ProfileControl(); //To get inputs from database if available.
        pc.readProfile(this);
        if(pc.getUD()!=null) {
            Log.d(Boolean.toString(pc.getMaritalStatus()),"222");
            if (pc.getMaritalStatus()) {
                radioGroupMS.check(R.id.radioBtnMarried);
            }
            else{
                radioGroupMS.check(R.id.radioBtnSingle);
            }
            if(pc.getFirstTimeBuyer()){
                radioGroupFTB.check(R.id.radioBtnYes);
            }
            else{
                radioGroupFTB.check(R.id.radioBtnNo);
            }
            if(pc.getCitizenship()){
                radioGroupCiti.check(R.id.radioBtnSG);
            }
            else{
                radioGroupCiti.check(R.id.radioBtnPR);
            }
            ageInput.setText(Integer.toString(pc.getAge()));
            grossMSalary.setText(Double.toString(pc.getGrossMonthlySalary()));

            if(pc.getFirstTimeBuyerPartner()){
                radioGroupFTB2.check(R.id.radioBtnYes2);
            }
            else{
                radioGroupFTB2.check(R.id.radioBtnNo2);
            }
            if(pc.getCitizenshipPartner()){
                radioGroupCiti2.check(R.id.radioBtnSG2);
            }
            else{
                radioGroupCiti2.check(R.id.radioBtnPR2);
            }
            ageInput2.setText(Integer.toString(pc.getAgePartner()));
            grossMSalary2.setText(Double.toString(pc.getGrossMonthlySalaryPartner()));

            if(radioGroupMS.getCheckedRadioButtonId()==R.id.radioBtnSingle){ //Blur out Partner's input if user is single.
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
            else if(radioGroupMS.getCheckedRadioButtonId()==R.id.radioBtnMarried){
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
                    ageInput2.setText("0"); //set partner's field to empty.
                    grossMSalary2.setText("0");
                    if(Integer.parseInt(ageInput.getText().toString())<35){
                        ageInput.setText("35");
                    }
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
                    ageInput2.setText("21"); //set partner's field to empty.
                    grossMSalary2.setText("0");
                }
            }
        });


        next.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                int radioGroupMScheckedID = radioGroupMS.getCheckedRadioButtonId();
                int radioGroupFTBcheckedID = radioGroupFTB.getCheckedRadioButtonId();
                int radioGroupFTB2checkedID = radioGroupFTB2.getCheckedRadioButtonId();
                int radioGroupCiticheckedID = radioGroupCiti.getCheckedRadioButtonId();
                int radioGroupCiti2checkedID = radioGroupCiti2.getCheckedRadioButtonId();
                Intent i = new Intent( ProfileUI.this, ProfileUI2.class);
                i.putExtra ("radioGroupMScheckedID",radioGroupMScheckedID);
                i.putExtra ("radioGroupFTBcheckedID",radioGroupFTBcheckedID);
                i.putExtra ("radioGroupCiticheckedID",radioGroupCiticheckedID);
                i.putExtra ("ageInputStr",ageInput.getText().toString());
                i.putExtra ("grossMSalaryStr",grossMSalary.getText().toString());

                i.putExtra ("radioGroupFTB2checkedID",radioGroupFTB2checkedID);
                i.putExtra ("radioGroupCiti2checkedID",radioGroupCiti2checkedID);
                i.putExtra ("ageInputStr2",ageInput2.getText().toString());
                i.putExtra ("grossMSalaryStr2",grossMSalary2.getText().toString());
                startActivity(i);

            }
        });




    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) { //set field to 0 if empty
        EditText grossMSalary = findViewById(R.id.grossMSalary);
        EditText grossMSalary2 = findViewById(R.id.grossMSalary2);

        double salarySingle = Double.parseDouble(grossMSalary.getText().toString());
        double salaryPartner = Double.parseDouble(grossMSalary2.getText().toString());
        if(v instanceof EditText) {
            EditText x = (EditText) v; //downcasting
            String value = x.getText().toString();
            if (value.isEmpty() == true) {
                x.setText("0");
            }
            if (!hasFocus) {
                int id = x.getId();
                if(id == findViewById(R.id.ageInput).getId()){ //if ageInput is edited
                    RadioGroup radioGroupMS = findViewById(R.id.radioGroupMS);
                    if((radioGroupMS.getCheckedRadioButtonId()==findViewById(R.id.radioBtnMarried).getId())){ //if button checked is married
                        if(Integer.parseInt(x.getText().toString())<21){ //check is age is less than 21
                            Toast.makeText(getApplicationContext(),"Please input age more than 21!",Toast.LENGTH_SHORT).show();
                            x.setText("21");
                        }
                        else if(Integer.parseInt(x.getText().toString())>65){
                            Toast.makeText(getApplicationContext(),"Please input age less than 65!",Toast.LENGTH_SHORT).show();
                            x.setText("21");
                        }
                    }
                    else if((radioGroupMS.getCheckedRadioButtonId()==findViewById(R.id.radioBtnSingle).getId())){
                        if(Integer.parseInt(x.getText().toString())<35) { //check is age is less than 35
                            Toast.makeText(getApplicationContext(), "Please input age more than 35!", Toast.LENGTH_SHORT).show();
                            x.setText("35");
                        }
                    }
                }
                else if(id == findViewById(R.id.ageInput2).getId()) { //if ageInput2 is edited
                    if(Integer.parseInt(x.getText().toString())<21) { //check is age is less than 21
                        Toast.makeText(getApplicationContext(), "Please input age more than 21!", Toast.LENGTH_SHORT).show();
                        x.setText("21");
                    }
                    else if(Integer.parseInt(x.getText().toString())>65){
                        Toast.makeText(getApplicationContext(),"Please input age less than 65!",Toast.LENGTH_SHORT).show();
                        x.setText("21");
                    }
                }
                else if(id == findViewById(R.id.grossMSalary).getId()) { //if user salary is edited
                    Log.d(x.getText().toString(),"11122");
                    salarySingle = Double.parseDouble(x.getText().toString());
                    if (Double.parseDouble(x.getText().toString()) < 0) { //check if salary is negative
                        Toast.makeText(getApplicationContext(), "Salary cannot be negative!", Toast.LENGTH_SHORT).show();
                        x.setText("0");
                    }
                    if(salaryPartner + salarySingle > 12000){
                        Toast toast = Toast.makeText(getApplicationContext(), "Your total salary is too high to be eligible for purchasing HDB", Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();
                        x.setText("0");
                    }
                }
                else if(id == findViewById(R.id.grossMSalary2).getId()) { //if partner salary is edited
                    salaryPartner = Double.parseDouble(x.getText().toString());
                    if (Double.parseDouble(x.getText().toString()) < 0) { //check if partner salary is negative
                        Toast.makeText(getApplicationContext(), "Salary cannot be negative!", Toast.LENGTH_SHORT).show();
                        x.setText("0");
                    }
                    if(salaryPartner + salarySingle > 12000){
                        Toast toast = Toast.makeText(getApplicationContext(), "Your total salary is too high to be eligible for purchasing HDB", Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();
                        x.setText("0");

                    }
                }
                if (id  == findViewById(R.id.grossMonthlySalary2).getId()){

                }

            }
        }
    }

}
