package com.example.igo;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
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
import java.util.concurrent.TimeUnit;

public class manualattendance extends AppCompatActivity {
    public static final String TAG = "ManualAttendance";

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
        Toserversend server = new Toserversend();
        Toserversend sendtoserver = new Toserversend();
        Context context = getApplicationContext();
        server.url = server.url + "/studentdetails";
        sendtoserver.url += "/updateattendance";
        //server.params.add("classname", "ct_2020_batch1");
        server.params.add("dept","CT");
        server.params.add("yr","3");
        server.params.add("batch","1");
        sendtoserver.params.add("tablename", "ct_2020_batch1");
        //System.out.println(server.url);
        Log.i(TAG, "\nparams created");
        Log.i(TAG, "\nparams sent");
        Boolean fg = false;
        fg = server.posting(context);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.manualattendance);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.manatt);
        recview_manatt adapter = new recview_manatt(server.studinf);
        // recview_manatt adapter=new recview_manatt(studlist);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        Button manattbtn = (Button) findViewById(R.id.manattsub);

        //final String strf=str;
        manattbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String str = new String();
                str = stringify(server.studinf);
                sendtoserver.params.remove("att");
                sendtoserver.params.add("att",str);
                System.out.println(str);
                sendtoserver.posting1(getApplicationContext());
//                if(bln)   Toast.makeText(getApplicationContext(),"Attendance Updated",Toast.LENGTH_SHORT).show();
//                else    Toast.makeText(getApplicationContext(),"Failed",Toast.LENGTH_SHORT).show();
                Intent i=new Intent(manualattendance.this,MainActivity2.class);
                startActivity(i);
            }
        });
//        if(respString!=null)    Toast.makeText(getApplicationContext(),respString,Toast.LENGTH_SHORT).show();
//        else   Toast.makeText(getApplicationContext(),"NULL",Toast.LENGTH_SHORT).show();
    }

    private String stringify(ArrayList<studentinfo_manatt> studinf) {
        int sz = studinf.size();
        String str = new String();
        str = "{";
        for (int i = 0; i < sz; i++) {
            str =str+ "\"" + studinf.get(i).getRegNo() + "\"";
            str =str+ ":";
            str +=(studinf.get(i).getCb())?"\"1\"":"\"0\"";
            if (i != sz - 1) str += ",";
        }
        str += "}";

        return str;
    }
}