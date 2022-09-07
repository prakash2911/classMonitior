package com.example.igo;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class recview_manatt extends RecyclerView.Adapter<recview_manatt.ViewHolder> {



    private studentinfo_manatt[] studlist_manatt;
    public recview_manatt(studentinfo_manatt[] stuinf){
        this.studlist_manatt=stuinf;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        public TextView studName;
        public TextView studregno;
        public CheckBox cb;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
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
        final studentinfo_manatt myListData = studlist_manatt[position];
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
                String status=(myListData.getCb()==false)?"ABSENT":"PRESENT";
            }
        });
        /*holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(),"click on item: "+myListData.getDescription(),Toast.LENGTH_LONG).show();
            }
        });*/
    }

    @Override
    public int getItemCount() {
        return studlist_manatt.length;
    }

}