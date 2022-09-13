package com.example.igo;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class recview_manatt extends RecyclerView.Adapter<recview_manatt.ViewHolder> {



    public ArrayList<studentinfo_manatt> studlist_manatt;
    public recview_manatt(ArrayList<studentinfo_manatt> stuinf){
        this.studlist_manatt=stuinf;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public TextView studName;
        public TextView studregno;
        public LinearLayout lilayout;
        public CheckBox cb;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.lilayout=(LinearLayout)itemView.findViewById(R.id.manatt_linlay);
            this.studName=(TextView) itemView.findViewById(R.id.studname_manatt);
            this.studregno=(TextView) itemView.findViewById(R.id.studregno_manatt);
            this.cb=(CheckBox) itemView.findViewById(R.id.presentcbox);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layouinflater=LayoutInflater.from(parent.getContext());
        View listview=layouinflater.inflate(R.layout.recvstudinfolay_manatt,parent,false);
        ViewHolder viewHolder = new ViewHolder(listview) {
            @Override
            public String toString() {
                return super.toString();
            }
        };
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final studentinfo_manatt myListData = studlist_manatt.get(position);
        holder.studName.setText(myListData.getName());
        holder.studregno.setText(myListData.getRegNo());
        holder.cb.setOnCheckedChangeListener(null);//in some cases, it will prevent unwanted situations
        holder.cb.setChecked(myListData.getCb());
       holder.cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @SuppressLint("WrongConstant")
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                myListData.setCb(b);
                holder.cb.setChecked(myListData.getCb());
                //notifyDataSetChanged();
                //String status=(myListData.getCb()==false)?"ABSENT":"PRESENT";
                }
        });

    }

    @Override
    public int getItemCount() {
        return studlist_manatt.size();
    }

}
