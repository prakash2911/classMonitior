package com.example.igo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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

    public SharedPreferences sf;
    public SharedPreferences.Editor editor;

    public void onBackPressed() {
        Intent i=new Intent(takeattendance.this,Homepage.class);
        startActivity(i);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.take_attendance);
        sf=getSharedPreferences("IGO3", Context.MODE_PRIVATE);
        editor=sf.edit();
        RelativeLayout mnatt=(RelativeLayout) findViewById(R.id.manualattendance);
        RelativeLayout aiatt=(RelativeLayout) findViewById(R.id.aiattendance);
        String clas=sf.getString("classname","none");//getIntent().getStringExtra("class");
        //Toast.makeText(getApplicationContext(),clas,Toast.LENGTH_SHORT).show();
        mnatt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(takeattendance.this,manualattendance.class);
                i.putExtra("class",clas);
                i.putExtra("type","ma");
                //overridePendingTransition(0, 0);
                startActivity(i);
                finish();
                //overridePendingTransition(0, 0);
            }
        });

        aiatt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(takeattendance.this,cameraopentest.class);
                i.putExtra("class",clas);
                i.putExtra("type","aa");
                startActivity(i);
                finish();
            }
        });
    }
}