package com.example.igo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

public class Attendanceselection extends AppCompatActivity {

    public void onBackPressed() {
        Intent i=new Intent(Attendanceselection.this,MainActivity2.class);
        startActivity(i);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.attendanceselect);
        List<String> years=new ArrayList<String>();
        years.add("I-Year");years.add("II-Year");years.add("III-Year");years.add("IV-Year");
        List<String> batches=new ArrayList<String>();
        batches.add("Batch-I");batches.add("Batch-II");
        Spinner year = (Spinner) findViewById(R.id.year_selec);
        Spinner batch = (Spinner) findViewById(R.id.batch_select);
        ArrayAdapter<String> yadapter =new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,years);
        yadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        year.setAdapter(yadapter);
        ArrayAdapter<String> badapter =new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,batches);
        yadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        batch.setAdapter(badapter);
        String yr=year.getItemAtPosition(year.getSelectedItemPosition()).toString();
        String bch=batch.getItemAtPosition(batch.getSelectedItemPosition()).toString();
        AppCompatButton button=(AppCompatButton) findViewById(R.id.toattendance);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(Attendanceselection.this,takeattendance.class);
                i.putExtra("year",yr);
                i.putExtra("batch",bch);
                startActivity(i);
            }
        });
    }
}