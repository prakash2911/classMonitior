package com.example.igo;

import android.content.Context;
import android.net.Uri;
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
import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class Toserversend {
    public static final String TAG="ToServersend";
    public String url;
    public String resp;
    public AsyncHttpClient client;
    public RequestParams params;
    public Boolean response;
    public ArrayList<studentinfo_manatt> studinf;
    //public studentinfo_manatt temp;
    Toserversend(){
        this.url=BaseUrl.url;
        this.client=new AsyncHttpClient();
        this.params=new RequestParams();
        studinf=new ArrayList<studentinfo_manatt>();
        response=false;
        //temp=new studentinfo_manatt("","",true);
        //this.response = "";
    }

    public boolean posting(Context context)
    {
        this.client.post(this.url,this.params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String str = new String(responseBody, StandardCharsets.UTF_8);
                String res=new String();
                //JsonParser jsonParser=new JsonParser();
                try {
                    JSONObject obj=new JSONObject(str);
                    Log.i(TAG,"JSON Created");
                    for(int i=0;i<obj.length();i++){
                        studentinfo_manatt temp=new studentinfo_manatt("","",true);
                        temp.setRegNo(obj.names().getString(i));
                        temp.setName(obj.get(obj.names().getString(i)).toString());
//                        System.out.println(studinf[i].getName()+"\n");
                        studinf.add(temp);

                    }
                    //Toast.makeText(context,"Created JSON Object:\n"+str,Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Toast.makeText(context,"Failure",Toast.LENGTH_SHORT).show();
                Log.i(TAG,"Failure");
                //response=null;
            }
        });
        Log.i(TAG,"Returned STUD");
        //Log.i(TAG,studinf[0].getName());
//        for(int i=0;i<5;i++){
//            System.out.println(studinf[i].getName()+"\n");
//        }
        return true;
    }

    public void posting1(Context context) {
        this.client.post(this.url,this.params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String str=new String(responseBody);
                System.out.print("\n\n"+str+"\n\n");
                //Toast.makeText(context.getApplicationContext(),str,Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Toast.makeText(context,"Failure",Toast.LENGTH_SHORT).show();
                Log.i(TAG,"Failure");
                //flag[0]=false;
                //response=null;
            }
        });
    }

    public void picsend(Context context) {
        //final String[] resp = {new String()};
        this.client.post(this.url,this.params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String str =new String(responseBody);
                resp=str;
                response=true;
                //if(response)                Toast.makeText(context.getApplicationContext(),"received:\n"+str,Toast.LENGTH_SHORT).show();
                //Toast.makeText(context.getApplicationContext(),"List1:"+resp,Toast.LENGTH_SHORT).show();
                //System.out.println("\n\nHI:"+resp+"\n\n");
//                printstr(context);
                //System.out.print("\n\n"+resp+"\n\n");
                //Toast.makeText(context.getApplicationConte
                //
                //
                //
                //
                //
                // xt(), resp,Toast.LENGTH_LONG).show();
//                try {
//                    JSONObject obj = new JSONObject(resp);
//                    System.out.println("JSON Created");
//                    for (int i = 0; i < obj.length(); i++) {
//                        JSONObject obej = obj.getJSONObject(obj.names().getString(i));
//                        studentinfo_manatt temp = new studentinfo_manatt("", "", true);
//                        temp.setRegNo(obj.names().getString(i));
//                        temp.setName(obej.names().getString(0));
//                        Boolean flag = (obej.get(obej.names().getString(0)).toString() == "1") ? true : false;
//                        temp.setCb(flag);
//                        System.out.println(temp.getRegNo() + "\t" + temp.getName() + "\t" + temp.getCb());
//                        //temp.setName(obj.get(obj.names().getString(i)).toString());
////                        System.out.println(studinf[i].getName()+"\n");
//                        studinf.add(temp);
//                    }
//                }catch (JSONException e){
//                    System.out.println(e);
//                }
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Toast.makeText(context,"Failures",Toast.LENGTH_SHORT).show();
                Log.i(TAG,"Failure");
                //flag[0]=false;
                //response=null;
            }
        });
    }
    public void printstr(Context context){
        Toast.makeText(context.getApplicationContext(),resp,Toast.LENGTH_SHORT).show();
    }

    //public String getResponse(){
//        return this.response;
//    }
}
