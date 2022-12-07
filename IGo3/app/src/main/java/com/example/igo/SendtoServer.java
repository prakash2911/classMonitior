package com.example.igo;

import android.content.Context;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.entity.mime.Header;

public class SendtoServer {
    public String url=BaseUrl.url;
    public AsyncHttpClient client;
    public RequestParams params;
    public ArrayList<Myclass_det> classes;
    public Myclass_det myclass;
    public String str;
    SendtoServer() {
        this.url = BaseUrl.url;
        this.client = new AsyncHttpClient();
        this.params = new RequestParams();
        classes=new ArrayList<Myclass_det>();
        //myclass=new Myclass_det();
    }
    public void recieveDet(Context context){
//        classes.add(new Myclass_det("CT_2020_BATCH1","CS6108","40"));
//        classes.add(new Myclass_det("CT_2020_BATCH2","CS6108","45"));
//        classes.add(new Myclass_det("CT_2020_BATCH1","CS6109","40"));
//        classes.add(new Myclass_det("CT_2020_BATCH2","CS6109","45"));
//        classes.add(new Myclass_det("CT_2020_BATCH1","CS6107","40"));
//        classes.add(new Myclass_det("CT_2020_BATCH2","CS6107","45"));
        this.client.post(this.url, this.params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] responseBody) {
                str = new String(responseBody);
                String res=new String();
                convert(str);
                //Toast.makeText(context, str, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] responseBody, Throwable error) {
                Toast.makeText(context, "Failure", Toast.LENGTH_SHORT).show();
            }
        });
        //classes.add(new Myclass_det("Class","Course","strength"));
    }
    public void convert(String str){
        try {
            JSONObject obj = new JSONObject(str);
            //Log.i(TAG, "JSON Created");
            for (int i = 0; i < obj.length(); i++) {
                JSONObject t = new JSONObject(obj.getJSONObject(obj.names().getString(i)).toString());
//                        Myclass_det
                myclass=new Myclass_det(t.get("class").toString(),t.get("course").toString(),t.get("strength").toString());
                classes.add(myclass);
                System.out.println("\n\n"+classes.size() + "\n\n");
                System.out.println("\n\nCreated:"+t.get("class").toString()+"\n\n");
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
    }
}
