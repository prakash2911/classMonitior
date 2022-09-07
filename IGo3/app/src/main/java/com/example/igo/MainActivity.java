package com.example.igo;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity {
    AsyncHttpClient client;
    RequestParams params;
    public String url="http://192.168.241.167:5000";
    @Override
    public void onBackPressed() {
        exit();
    }

    private void exit() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to exit?").setCancelable(false);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(getApplicationContext(), "Bye!", Toast.LENGTH_SHORT).show();
                Intent it = new Intent(Intent.ACTION_MAIN);
                it.addCategory(Intent.CATEGORY_HOME);
                it.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
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
        alert.setTitle("EXIT ?");
        alert.show();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        EditText uname=(EditText)findViewById(R.id.userid);
        EditText password=(EditText)findViewById(R.id.passwrd);
        client=new AsyncHttpClient();
        Button b = (Button) findViewById(R.id.sign_in);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String log_in=url+"/login";
                params=new RequestParams();
                params.add("uname",uname.getText().toString());
                params.add("pass",password.getText().toString());
                /*client.post(log_in, params, new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                        String str=new String(responseBody);
                        if(str.equals("true")){
                        //if(true){*/
                            Intent i=new Intent(MainActivity.this,MainActivity2.class);
                            startActivity(i);
                        /*}
                        else{
                            Toast.makeText(getApplicationContext(),"Incorrect Credentials",Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                        Toast.makeText(getApplicationContext(),"Server Down",Toast.LENGTH_SHORT).show();
                    }
                });*/
            }
        });
    }
}