package com.example.igo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.media.CamcorderProfile;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class takeattendance extends AppCompatActivity {

    public void onBackPressed() {
        Intent i=new Intent(takeattendance.this,Attendanceselection.class);
        startActivity(i);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.take_attendance);
        RelativeLayout mnatt=(RelativeLayout) findViewById(R.id.manualattendance);
        mnatt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(takeattendance.this,manualattendance.class);
                startActivity(i);
            }
        });
        RelativeLayout aiatt=(RelativeLayout) findViewById(R.id.aiattendance);
        aiatt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(takeattendance.this,cameraopentest.class);
                startActivity(i);
            }
        });
    }
}