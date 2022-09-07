package com.example.igo;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonParser;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;

import java.nio.charset.StandardCharsets;

import cz.msebera.android.httpclient.Header;

public class Toserversend {
    public static final String TAG="ToServersend";
    public String url;
    public AsyncHttpClient client;
    public RequestParams params;
    public String response;
    Toserversend(){
        this.url="http://192.168.241.167:5000";
        this.client=new AsyncHttpClient();
        this.params=new RequestParams();
        this.response = "";
    }

    public void posting(Context context,studentinfo_manatt[] studinf)
    {

        this.client.post(this.url, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String str = new String(responseBody, StandardCharsets.UTF_8);
                String res=new String();
                JsonParser jsonParser=new JsonParser();
                try {
                    JSONObject obj=new JSONObject(str);
                    Log.i(TAG,"JSON Created");
                    for(int i=0;i<60;i++){
//                        res+=obj.names().getString(i);
//                        res+=":";
//                        res+=obj.get(obj.names().getString(i));
//                        res+="\n";
                        //studentinfo_manatt stud=new studentinfo_manatt();
                        studinf[i].setRegNo(obj.names().getString(i));
                        studinf[i].setName(obj.get(obj.names().getString(i)).toString());
                        System.out.println(studinf[i].getName()+"\n");
                    }
                    Toast.makeText(context,"Created JSON Object:\n"+res,Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                //Toast.makeText(context,"Success"+str,Toast.LENGTH_SHORT).show();
                //System.out.println(str);
                //Log.i(TAG,"Success");
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Toast.makeText(context,"Failure",Toast.LENGTH_SHORT).show();
                Log.i(TAG,"Failure");
                response=null;
            }
        });
        Log.i(TAG,"Returned STUD");
        //Log.i(TAG,studinf[0].getName());
//        for(int i=0;i<5;i++){
//            System.out.println(studinf[i].getName()+"\n");
//        }
    }

    public String getResponse(){
        return this.response;
    }
}
