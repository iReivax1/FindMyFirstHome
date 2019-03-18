package com.example.findmyfirsthome.Boundary;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.example.findmyfirsthome.R;

public class HDBDevelopmentUI extends AppCompatActivity {

    Switch sw;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.development_detail_ui); // set desired fragment for the first time

        final FragmentManager fm = getSupportFragmentManager();
        final FragmentTransaction ft = fm.beginTransaction();
        final HDBFragment_recommended FirstFragment = new HDBFragment_recommended();

        ft.add(R.id.display_development, FirstFragment); // The id specified here identifies which ViewGroup to append the Fragment to.
        ft.commit();

        sw = (Switch) findViewById(R.id.ToggleSwitch);
        sw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Fragment changeFragment = null;
                if(isChecked){
                    changeFragment = new HDBFragement_all();
                }
                else changeFragment = new HDBFragment_recommended();
                ft.replace(R.id.display_development, changeFragment);
                ft.commitNow();
            }
        });
    }

    public Context getContext(){
        return this.getApplicationContext();
    }
}
