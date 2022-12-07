package com.example.igo;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class Homepage extends AppCompatActivity {
    public SendtoServer server;
    private Handler mHandler;
    private ProgressDialog mProgressBar;
    public SharedPreferences sf;//=getSharedPreferences("IGO3",Context.MODE_PRIVATE);
    public SharedPreferences.Editor editor;//=sf.edit();
    public Spinner sp;
    //public ArrayList<Myclass_det> classes;

    @Override
    public void onBackPressed() {
        Toast.makeText(getApplicationContext(), "Bye!", Toast.LENGTH_SHORT).show();
        Intent it = new Intent(Intent.ACTION_MAIN);
        it.addCategory(Intent.CATEGORY_HOME);
        it.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(it);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homepage);
        //classes=new ArrayList<Myclass_det>();
        sf = getSharedPreferences("IGO3", Context.MODE_PRIVATE);
        editor = sf.edit();
        server = new SendtoServer();
        server.url += "/viewclass";
        String teacher_id = sf.getString("teacher_id", "no");
        server.params.add("teacher_id", teacher_id);
        notificationDialog();
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                Looper.prepare();
                server.recieveDet(getApplicationContext());
                Looper.loop();
            }
        });
        t1.start();
        //Toast.makeText(getApplicationContext(),teacher_id,Toast.LENGTH_SHORT).show();
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.my_class1);
        //System.out.println("Tag");
        int i = 0;
        System.out.println("\n\n classes size" + server.str + "\n\n");
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        recview_myclasses adapter = new recview_myclasses(server.classes, editor);
        recyclerView.setAdapter(adapter);
        List<String> spinner_items = new ArrayList<>();
        spinner_items.add("");
        spinner_items.add(teacher_id);
        spinner_items.add("Logout");
        sp = (Spinner) findViewById(R.id.spinner_homepage);
        ArrayAdapter<String> yadapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, spinner_items);
        yadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp.setAdapter(yadapter);
        sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = parent.getItemAtPosition(position).toString();
                if (selectedItem.equalsIgnoreCase("Logout")) {
                    exit();
                } else if (selectedItem.equalsIgnoreCase(teacher_id)) {
                    Toast.makeText(getApplicationContext(), teacher_id, Toast.LENGTH_SHORT).show();
                    sp.setSelection(0);
                }
            } // to close the onItemSelected

            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(getApplicationContext(), "nothing selected", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void notificationDialog() {
        mHandler = new Handler();
        mProgressBar = new ProgressDialog(Homepage.this);
        mProgressBar.setMax(100);
        mProgressBar.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        mProgressBar.show();
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i <= 100; i++) {
                    final int currentProgressCount = i;
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            mProgressBar.setProgress(currentProgressCount);
                        }
                    });
                }
                //adapter.updateData(server.studinf);
            }
        }).start();
        //mProgressBar.dismiss();
    }

    private void exit() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure You want to LOGOUT?").setCancelable(false);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //Toast.makeText(getApplicationContext(),"Bye!",Toast.LENGTH_SHORT).show();
                editor.remove("teacher_id");
                editor.apply();
                Intent ij = new Intent(Homepage.this, MainActivity.class);
                startActivity(ij);
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                sp.setSelection(0);
                dialogInterface.cancel();
            }
        });
        AlertDialog alert = builder.create();
        alert.setTitle("LOGOUT ?");
        alert.show();
    }
}