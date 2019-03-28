package com.example.findmyfirsthome.Controller;

import android.Manifest;
import android.app.DownloadManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;

public class DownloadFileManager extends AppCompatActivity {

    private Context context = this;
    private static int REQUEST_CODE = 1;
    private long refid;
    private DownloadManager manager;
    ArrayList<Long> list = new ArrayList<>();

    public DownloadFileManager() {

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
    }


    public void download(String urlKML) {

        Uri url = Uri.parse(urlKML);
        DownloadManager.Request request = new DownloadManager.Request(url);
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE);
        request.setTitle("kml");
        request.setDescription("download");
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "/govData/");
        manager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
        refid = manager.enqueue(request);


        Log.e("kml", "download" + refid);

        list.add(refid);
    }




}
