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
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonParser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

public class manualattendance extends AppCompatActivity {
    public static final String TAG="ManualAttendance";
    @Override
    public void onBackPressed() {
        exit();
    }

    private void exit() {
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setMessage("Changes are Not Submitted!\nAre you sure to exit?").setCancelable(false);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //Toast.makeText(getApplicationContext(),"Bye!",Toast.LENGTH_SHORT).show();
                Intent it =new Intent(manualattendance.this,takeattendance.class);
                startActivity(it);
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        AlertDialog alert=builder.create();
        alert.setTitle("Alert ?");
        alert.show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        TextView txtvw=(TextView)findViewById(R.id.heading);
        Toserversend server=new Toserversend();
        Context context=getApplicationContext();
        server.url=server.url+"/studentdetails";
        //Toast.makeText(getApplicationContext(),server.url,Toast.LENGTH_SHORT).show();
//        Log.i(TAG,server.url);
        server.params.add("classname","ct_2020_batch1");
        //System.out.println(server.url);
        Log.i(TAG,"\nparams created");
        Log.i(TAG,"\nparams sent");
        //byte[] resp=server.getResponse();
        //Toast.makeText(getApplicationContext(),res,Toast.LENGTH_SHORT).show();
        //studentinfo_manatt[] respString=new studentinfo_manatt[60];
        studentinfo_manatt[] studinf=new studentinfo_manatt[60];
        for(int i=0;i<60;i++){
            studinf[i]=new studentinfo_manatt("","",true);
        }
        server.posting(context,studinf);
        //Log.i(TAG,"\n\n"+respString[0].getName()+"\n\n");
        //txtvw.setText();
        studentinfo_manatt[] studlist=new studentinfo_manatt[]{
                new studentinfo_manatt("Guru Raman","2020503510",true),
                new studentinfo_manatt("Jaya Prakash","2020503515",true),
                new studentinfo_manatt("Prakash","2020503530",true),
                new studentinfo_manatt("Siddarthan","2020503544",true),
        };
        setContentView(R.layout.manualattendance);
        RecyclerView recyclerView=(RecyclerView) findViewById(R.id.manatt);
        recview_manatt adapter=new recview_manatt(studinf);
        //recview_manatt adapter=new recview_manatt(respString);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

//        if(respString!=null)    Toast.makeText(getApplicationContext(),respString,Toast.LENGTH_SHORT).show();
//        else   Toast.makeText(getApplicationContext(),"NULL",Toast.LENGTH_SHORT).show();
    }
}