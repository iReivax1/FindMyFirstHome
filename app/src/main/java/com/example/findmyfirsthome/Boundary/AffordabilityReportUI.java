package com.example.findmyfirsthome.Boundary;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import com.example.findmyfirsthome.Controller.AffordabilityReportController;
import com.example.findmyfirsthome.R;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Arrays;

public class AffordabilityReportUI extends AppCompatActivity {

    TextView tvMaxpropertyprice, tvMaxmortgage, tvMaxtenure, tvShg, tvAhg,
            tvPropertyname, tvPropertyprice, tvFlattype, tvDownpaymentreq, tvLoanreq, tvRepaymentsum;

    AffordabilityReportController arc = new AffordabilityReportController();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.aff_rep_ui);
        Intent intent = getIntent();
        String hdbName = intent.getStringExtra("hdbName");
        String flatType = intent.getStringExtra("flatType");

        // initializing TextViews and appending values
        tvPropertyname = (TextView) findViewById(R.id.property_name);
        tvPropertyname.append(hdbName);
        tvFlattype = (TextView) findViewById(R.id.flat_type);
        tvFlattype.append(flatType);
        setFixedInfo();
        setHDBDependentInfo();
    }

    private void setFixedInfo(){
        ArrayList<String> fixedInfo = arc.getFixedInfo();
        if (fixedInfo.size() == 0)
            return;

        tvMaxpropertyprice = (TextView) findViewById(R.id.max_property_price);
        tvMaxmortgage = (TextView) findViewById(R.id.max_mortgage);
        tvMaxtenure = (TextView) findViewById(R.id.max_tenure);
        tvShg = (TextView) findViewById(R.id.shg);
        tvAhg = (TextView) findViewById(R.id.ahg);

        tvMaxmortgage.append(fixedInfo.get(0));
        tvMaxtenure.append(fixedInfo.get(1));
        tvMaxpropertyprice.append(fixedInfo.get(2));
        tvAhg.append(fixedInfo.get(3));
        tvShg.append(fixedInfo.get(4));
    }

    private void setHDBDependentInfo(){
        ArrayList<String> HDBDependentInfo = arc.getHDBDependentInfo();
        if (HDBDependentInfo.size() == 0)
            return;

        tvPropertyprice = (TextView) findViewById(R.id.property_price);
        tvPropertyname.append(HDBDependentInfo.get(0));
        tvDownpaymentreq = (TextView) findViewById(R.id.downpayment_req);
        tvDownpaymentreq.append(HDBDependentInfo.get(1));
        tvLoanreq = (TextView) findViewById(R.id.loan_req);
        tvLoanreq.append(HDBDependentInfo.get(2));
        tvRepaymentsum = (TextView) findViewById(R.id.repayment_sum);
        tvRepaymentsum.append(HDBDependentInfo.get(3));
    }
}
