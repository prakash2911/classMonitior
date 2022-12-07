package com.example.igo;

import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class recview_myclasses extends RecyclerView.Adapter<recview_myclasses.ViewHolder> {
    public ArrayList<Myclass_det> myclass;
    //public SharedPreferences sf;
    public SharedPreferences.Editor editor;
    public recview_myclasses(ArrayList<Myclass_det> mclass,SharedPreferences.Editor editor){
        //myclass=new ArrayList<>();
        this.myclass=mclass;
        this.editor=editor;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public TextView class_nme;
        public TextView course_nme;
        public TextView stren;
        public AppCompatButton attbtn;

        public ViewHolder(View listview) {
            super(listview);
            this.class_nme=(TextView) listview.findViewById(R.id.class_name);
            this.course_nme=(TextView) listview.findViewById(R.id.course_name);
            this.stren=(TextView) listview.findViewById(R.id.strength);
            this.attbtn=(AppCompatButton) listview.findViewById(R.id.tkattendancebtn);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutinflater=LayoutInflater.from(parent.getContext());
        View listview=layoutinflater.inflate(R.layout.recv_myclass,parent,false);
        ViewHolder viewHolder=new ViewHolder(listview);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Myclass_det myclss=myclass.get(position);
        String clasnam=new String(myclss.getClassName());
        clasnam=clasnam.replaceAll("_","-");
        holder.class_nme.setText(clasnam);//myclss.getClassName());
        holder.course_nme.setText(myclss.getCourseName());
        holder.stren.setText(myclss.getStrength());
        holder.attbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(view.getContext(),myclss.getClassName(),Toast.LENGTH_SHORT).show();
                Intent i=new Intent(holder.itemView.getContext(),takeattendance.class);
                editor.putString("classname",myclss.getClassName());
                editor.apply();
//                i.putExtra("class",myclss.getClassName());
                //i.putExtra("class","CT_2020_BATCH1");
                holder.itemView.getContext().startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return myclass.size();
    }

}
