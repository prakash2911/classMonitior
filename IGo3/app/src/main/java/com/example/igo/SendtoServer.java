package com.example.igo;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class SendtoServer {
    public static final String TAG = "SendtoServer";
    public String url;
    public AsyncHttpClient client;
    public RequestParams params;
    public ArrayList<Myclass_det> classes;

    SendtoServer() {
        this.url = BaseUrl.url;
        this.client = new AsyncHttpClient();
        this.params = new RequestParams();
        classes=new ArrayList<Myclass_det>();
    }

    public boolean recieveDet(Context context) {
        classes.add(new Myclass_det("CT_2020_BATCH1","CS6108","50"));
        classes.add(new Myclass_det("Class","Course","strength"));

        //
        this.client.post(this.url, this.params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String str = new String(responseBody,StandardCharsets.UTF_8);
                String res=new String();
                convert(str);
                Toast.makeText(context, str, Toast.LENGTH_SHORT).show();
                //classes.add(new Myclass_det("Class","Course","strength"));
                //JsonParser jsonParser=new JsonParser();

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Toast.makeText(context, "Failure", Toast.LENGTH_SHORT).show();
                Log.i(TAG, "Failure");
            }
        });
        return true;
        //classes.add(new Myclass_det("Class","Course","strength"));
    }

    public void convert(String str){
        classes.add(new Myclass_det("Class","Course","strength"));
        System.out.println("Added");
//        Myclass_det myclass=new Myclass_det("","","");
//        try {
//            JSONObject obj=new JSONObject(str);
//            Log.i(TAG,"JSON Created");
//            for(int i=0;i<obj.length();i++){
//                JSONObject t=new JSONObject(obj.getJSONObject(obj.names().getString(i)).toString());
//                //Myclass_det temp=new Myclass_det(t.get("class").toString(),t.get("course").toString(),t.get("strength").toString());
//
//                myclass.setClassName(t.getString("class"));
//                myclass.setCourseName(t.getString("course"));
//                myclass.setStrength(t.getString("strength"));
//                classes.add(myclass);
//            }
//            //Toast.makeText(context,"Created JSON Object:\n"+str,Toast.LENGTH_SHORT).show();
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
    }
}

