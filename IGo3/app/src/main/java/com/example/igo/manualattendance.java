package com.example.igo;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonParser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class manualattendance extends AppCompatActivity {
    public static final String TAG = "ManualAttendance";
    public Toserversend server;
    public RecyclerView recyclerView;
    private Handler mHandler;
    private recview_manatt adapter;
    private ProgressDialog mProgressBar;
    @Override
    public void onBackPressed() {
        exit();
    }

    private void exit() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Changes are Not Submitted!\nAre you sure to exit?").setCancelable(false);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //Toast.makeText(getApplicationContext(),"Bye!",Toast.LENGTH_SHORT).show();
                Intent it = new Intent(manualattendance.this, takeattendance.class);
                startActivity(it);
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        AlertDialog alert = builder.create();
        alert.setTitle("Alert ?");
        alert.show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.manualattendance);
        Context context = getApplicationContext();
        String chec=getIntent().getStringExtra("type");
        String classname = getIntent().getStringExtra("class").toLowerCase();
        server = new Toserversend();
        if(chec.equalsIgnoreCase("ma")) {
            server.url = server.url + "/studentdetails";
            server.params.add("classname", classname.toLowerCase());
            //final Boolean fg;
            Context contet=this.getApplicationContext();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    Looper.prepare();
                    Boolean fg = server.posting(contet);
                    Looper.loop();
                }
            }).start();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    Looper.prepare();
                    notificationDialog();
                    Looper.loop();
                }
            }).start();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        if(chec.equalsIgnoreCase("aa")){
            String str=getIntent().getStringExtra("List");
            //Toast.makeText(getApplicationContext(),"List:"+str,Toast.LENGTH_SHORT).show();
            //Toast.makeText(getApplicationContext(),str,Toast.LENGTH_SHORT).show();
            System.out.println("Calling updateToServer"+str);
            Updatetoserver(str);
        }
        Toserversend sendtoserver = new Toserversend();
        sendtoserver.url += "/updateattendance";
        sendtoserver.params.add("tablename", classname);
        //System.out.println(server.url);
        Log.i(TAG, "\nparams created");
        Log.i(TAG, "\nparams sent");
        recyclerView = (RecyclerView) findViewById(R.id.manatt);
        // recview_manatt adapter=new recview_manatt(studlist);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        //notificationDialog();
        adapter = new recview_manatt(server.studinf);
        recyclerView.setAdapter(adapter);
        Button manattbtn = (Button) findViewById(R.id.manattsub);
        manattbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(manualattendance.this);
                builder.setMessage("Total Number 0f students : "+adapter.getItemCount()+"\nPresent="+(adapter.getItemCount()-adapter.absentcount())+"\nAbsent="+adapter.absentcount()+"\nAre you sure to submit?").setCancelable(false);
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String str = new String();
                        str = stringify(server.studinf);
                        sendtoserver.params.remove("att");
                        sendtoserver.params.add("att", str);
                        System.out.println(str);
                        sendtoserver.posting1(getApplicationContext());
                        Intent in = new Intent(manualattendance.this, Homepage.class);
                        startActivity(in);
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
                AlertDialog alert = builder.create();
                alert.setTitle("SUBMIT ?");
                alert.show();
                }
        });
//        if(respString!=null)    Toast.makeText(getApplicationContext(),respString,Toast.LENGTH_SHORT).show();
//        else   Toast.makeText(getApplicationContext(),"NULL",Toast.LENGTH_SHORT).show();
    }

    private void notificationDialog() {
        mHandler=new Handler();
        mProgressBar= new ProgressDialog(manualattendance.this);
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
                    //Update the value background thread to UI thread
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

    private void Updatetoserver(String str) {
        try {
            System.out.println(str);
            JSONObject obj=new JSONObject(str);
            System.out.println("JSON Created");
            for(int i=0;i<obj.length();i++){
                JSONObject obej=obj.getJSONObject(obj.names().getString(i));
                studentinfo_manatt temp=new studentinfo_manatt("","",true);
                temp.setRegNo(obj.names().getString(i));
                temp.setName(obej.names().getString(0));
                Boolean flag=(obej.get(obej.names().getString(0)).toString().equalsIgnoreCase("1"))?true:false;
                temp.setCb(flag);
                System.out.println(temp.getRegNo()+"\t"+temp.getName()+"\t"+temp.getCb());
                //temp.setName(obj.get(obj.names().getString(i)).toString());
//                        System.out.println(studinf[i].getName()+"\n");
                server.studinf.add(temp);
            }
            //Toast.makeText(context,"Created JSON Object:\n"+str,Toast.LENGTH_SHORT).show();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private String stringify(ArrayList<studentinfo_manatt> studinf) {
        int sz = studinf.size();
        String str = new String();
        str = "{";
        for (int i = 0; i < sz; i++) {
            str = str + "\"" + studinf.get(i).getRegNo() + "\"";
            str = str + ":";
            str += (studinf.get(i).getCb()) ? "\"1\"" : "\"0\"";
            if (i != sz - 1) str += ",";
        }
        str += "}";

        return str;
    }
}