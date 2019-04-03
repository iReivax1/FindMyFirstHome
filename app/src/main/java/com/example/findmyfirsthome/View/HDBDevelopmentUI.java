package com.example.findmyfirsthome.View;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.findmyfirsthome.Presenter.HDBDevelopmentController;
import com.example.findmyfirsthome.Presenter.HDBViewPagerAdapter;
import com.example.findmyfirsthome.R;

import java.util.List;

public class HDBDevelopmentUI extends AppCompatActivity implements View.OnClickListener {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private HDBViewPagerAdapter adapter;
    private Button recalculate;
    private TextView footerText;
    private HDBDevelopmentController hdbc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.display_development);

        tabLayout = (TabLayout) findViewById(R.id.tablayout_id);
        viewPager = (ViewPager) findViewById(R.id.viewpager_id);
        adapter = new HDBViewPagerAdapter(getSupportFragmentManager());
        recalculate = (Button) findViewById(R.id.footer_button);
        footerText = (TextView) findViewById(R.id.footer_text);

        //display footerText
        hdbc = new HDBDevelopmentController(this);
        List<String> footerDetails = hdbc.getFooterDetails();
        footerText.setText("Maximum Property Purchase Price: $" + footerDetails.get(0) +
                "\nMaximum Mortgage Amount: $" + footerDetails.get(1) +
                "\nMaximum Mortgage Term: " + footerDetails.get(2) + " years");

        // Add Fragment here
        adapter.AddFragment(new HDBFragment_Rec(), "Recommended");
        adapter.AddFragment(new HDBFragment_All(), "Show All");

        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setSelectedTabIndicatorColor(Color.parseColor("#000080"));
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
                Intent intent = new Intent(this, ProfileUI.class); //replace HDBDevelopmentUI w Form
                startActivity(intent);
        }
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }

    public Context getContext(){
        return this.getApplicationContext();
    }
}
