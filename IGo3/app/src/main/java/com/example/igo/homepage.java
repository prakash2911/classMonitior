package com.example.igo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class homepage extends AppCompatActivity {
    public SendtoServer server;
    public ArrayList<Myclass_det> classes;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homepage);
        classes=new ArrayList<Myclass_det>();
        server=new SendtoServer();
        server.url+="/viewclass";
        server.params.add("teacher_id","jp@tn");
        //server.recieveDet(getApplicationContext());
        classes=recieveDet(getApplicationContext());
        //System.out.println(classes.get(0).getClassName()+classes.get(0).getCourseName()+classes.get(0).getStrength());
        RecyclerView recyclerView=(RecyclerView) findViewById(R.id.myclasses);
        System.out.println("Tag");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //ArrayList<Myclass_det> myadsp=new ArrayList<>();
        //myadsp.add(new Myclass_det("CT_2020_BATCH1","CS6108","50"));
        int i=0;
//        Myclass_det myclss=server.classes.get(i);
//        System.out.println(myclss.getClassName()+" "+myclss.getCourseName()+" "+myclss.getStrength());
        recview_myclasses adapter=new recview_myclasses(classes);
        //recview_myclasses adapter=new recview_myclasses(myadsp);
        //System.out.println(server.classes.get(0).getClassName());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }
    public ArrayList<Myclass_det> recieveDet(Context context) {
//        classes.add(new Myclass_det("CT_2020_BATCH1","CS6108","50"));
//        classes.add(new Myclass_det("Class","Course","strength"));

        //
        server.client.post(server.url, server.params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String str = new String(responseBody, StandardCharsets.UTF_8);
                String res=new String();
                Myclass_det myclass=new Myclass_det("","","");
                try {
                    JSONObject obj = new JSONObject(str);
                    //Log.i(TAG, "JSON Created");
                    for (int i = 0; i < obj.length(); i++) {
                        JSONObject t = new JSONObject(obj.getJSONObject(obj.names().getString(i)).toString());
                        Myclass_det temp=new Myclass_det(t.get("class").toString(),t.get("course").toString(),t.get("strength").toString());
                        classes.add(temp);
//                        myclass.setClassName(t.getString("class"));
//                        myclass.setCourseName(t.getString("course"));
//                        myclass.setStrength(t.getString("strength"));
//                        classes.add(myclass);
                        //System.out.println("\n\nuploaded\n\n");
                    }
                    //Toast.makeText(context,"Created JSON Object:\n"+str,Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Toast.makeText(context, str, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Toast.makeText(context, "Failure", Toast.LENGTH_SHORT).show();
                //Log.i(TAG, "Failure");
            }
        });
        return classes;
        //classes.add(new Myclass_det("Class","Course","strength"));
    }
}