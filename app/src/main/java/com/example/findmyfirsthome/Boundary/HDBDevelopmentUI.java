package com.example.findmyfirsthome.Boundary;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.findmyfirsthome.R;

public class HDBDevelopmentUI extends AppCompatActivity implements View.OnClickListener {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private HDBViewPagerAdapter adapter;
    private Button recalculate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.display_development);

        tabLayout = (TabLayout) findViewById(R.id.tablayout_id);
        viewPager = (ViewPager) findViewById(R.id.viewpager_id);
        adapter = new HDBViewPagerAdapter(getSupportFragmentManager());
        recalculate = (Button) findViewById(R.id.footer_button);

        // Add Fragment here
        adapter.AddFragment(new HDBFragment_Rec(), "Recommended");
        adapter.AddFragment(new HDBFragment_All(), "Show All");

        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

        //Remove Shadow from the action bar
        //ActionBar actionBar = getSupportActionBar();
        //actionBar.setElevation(0);

        //set on click listener for button to go to intent
        recalculate.setOnClickListener(this);
    }

    @Override
    public void onClick(View view){
        switch (view.getId()) {
            case R.id.footer_button:
                Intent intent = new Intent(this, HDBDevelopmentUI.class); //replace HDBDevelopmentUI w Form
                startActivity(intent);
        }
    }

    public Context getContext(){
        return this.getApplicationContext();
    }
}
