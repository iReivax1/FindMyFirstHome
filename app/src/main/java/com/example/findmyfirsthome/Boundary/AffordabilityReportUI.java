package com.example.findmyfirsthome.Boundary;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.findmyfirsthome.Controller.AffordabilityReportController;
import com.example.findmyfirsthome.R;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.PdfTemplate;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
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

        Button download = (Button) findViewById(R.id.button_generatepdf);
        download.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                takeScreenshot(v);
            }
            });
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
        tvPropertyprice.append(HDBDependentInfo.get(0));
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
        Document document = new Document();
        try {
            // image naming and path  to include sd card  appending name you choose for file
            File f = new File(Environment.getExternalStorageDirectory()+File.separator+"Download"+File.separator+now+".jpg");
            f.getParentFile().mkdirs();
            f.createNewFile();
            FileOutputStream outputStream = new FileOutputStream(f);
            // create bitmap screen capture
            View v1 = getWindow().getDecorView().getRootView();
            v1.setDrawingCacheEnabled(true);
            Bitmap bitmap = Bitmap.createBitmap(v1.getDrawingCache());
            v1.setDrawingCacheEnabled(false);
            int quality = 100;
            bitmap.compress(Bitmap.CompressFormat.JPEG, quality, outputStream);
            outputStream.flush();
            outputStream.close();

            String directoryPath = (Environment.getExternalStorageDirectory()+File.separator+"Download"+File.separator+now+".pdf");
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(directoryPath));
            document.open();
            Image image = Image.getInstance(f.toString());
            float scaler = ((document.getPageSize().getWidth() - document.leftMargin()
                    - document.rightMargin() +45) / image.getWidth()) * 100; // 0 means you have no indentation. If you have any, change it.
            image.scalePercent(scaler);
            image.setAlignment(Image.ALIGN_CENTER | Image.ALIGN_TOP);

            document.add(image);
            document.close();

            Toast.makeText(getApplicationContext(), "Saved as PDF!", Toast.LENGTH_SHORT).show();

        } catch (Throwable e) {
            Toast.makeText(getApplicationContext(), "Unable to save as PDF!", Toast.LENGTH_SHORT).show();
            // Several error may come out with file handling or DOM
            e.printStackTrace();
        }
    }

    public Image cropImage(PdfWriter writer, Image image, float leftReduction, float rightReduction, float topReduction, float bottomReduction) throws DocumentException {
        float width = image.getScaledWidth();
        float height = image.getScaledHeight();
        PdfTemplate template = writer.getDirectContent().createTemplate(
                width - leftReduction - rightReduction,
                height - topReduction - bottomReduction);
        template.addImage(image,
                width, 0, 0,
                height, -leftReduction, -bottomReduction);
        return Image.getInstance(template);
    }
}
