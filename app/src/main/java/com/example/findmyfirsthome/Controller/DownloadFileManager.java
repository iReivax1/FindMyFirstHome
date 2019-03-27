package com.example.findmyfirsthome.Controller;

import android.Manifest;
import android.app.DownloadManager;
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

    private DownloadManager downloadManager;
    private long refid;

    ArrayList<Long> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    public void download(String urlKML){
        Uri url = Uri.parse(urlKML);
        DownloadManager.Request request = new DownloadManager.Request(url);
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE);
        request.setAllowedOverRoaming(false);
        request.setTitle("kml");
        request.setDescription("download");
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "/govData/" );

        refid = downloadManager.enqueue(request);


        Log.e("kml", "download" + refid);

        list.add(refid);
    }

    public  boolean isStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                return true;
            } else {

                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                return false;
            }
        }
        else { //permission is automatically granted on sdk<23 upon installation
            return true;
        }
    }
}
