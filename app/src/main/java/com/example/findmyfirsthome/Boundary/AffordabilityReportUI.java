package com.example.findmyfirsthome.Boundary;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import com.example.findmyfirsthome.Controller.AffordabilityReportController;
import com.example.findmyfirsthome.R;

import org.w3c.dom.Text;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

public class AffordabilityReportUI extends AppCompatActivity {

    private TextView tvMaxpropertyprice, tvMaxmortgage, tvMaxtenure, tvShg, tvAhg,
            tvPropertyname, tvPropertyprice, tvFlattype, tvDownpaymentreq, tvLoanreq, tvRepaymentsum;

    private AffordabilityReportController arc;
    private View view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.aff_rep_ui);
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        String hdbName = extras.getString("estateName");
        String flatType = extras.getString("FlatType");

        arc = new AffordabilityReportController(this, hdbName, flatType);

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

    private void takeScreenshot(View view) {
        Date now = new Date();
        android.text.format.DateFormat.format("yyyy-MM-dd_hh:mm:ss", now);

        try {
            // image naming and path  to include sd card  appending name you choose for file
            String mPath = Environment.getExternalStorageDirectory().toString() + "/" + now + ".jpg";

            // create bitmap screen capture
            View v1 = getWindow().getDecorView().getRootView();
            v1.setDrawingCacheEnabled(true);
            Bitmap bitmap = Bitmap.createBitmap(v1.getDrawingCache());
            v1.setDrawingCacheEnabled(false);
            System.out.println("HERE");
            File imageFile = new File(mPath);

            FileOutputStream outputStream = new FileOutputStream(imageFile);
            int quality = 100;
            bitmap.compress(Bitmap.CompressFormat.JPEG, quality, outputStream);
            outputStream.flush();
            outputStream.close();

            //openScreenshot(imageFile);
        } catch (Throwable e) {
            // Several error may come out with file handling or DOM
            e.printStackTrace();
        }
    }
}
