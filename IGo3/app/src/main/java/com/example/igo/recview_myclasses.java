package com.example.igo;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class recview_myclasses extends RecyclerView.Adapter<recview_myclasses.ViewHolder> {
    public ArrayList<Myclass_det> myclass;
    public recview_myclasses(ArrayList<Myclass_det> mclass){
        //myclass=new ArrayList<>();
        this.myclass=mclass;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public TextView class_nme;
        public TextView course_nme;
        public TextView stren;

        public ViewHolder(View listview) {
            super(listview);
            this.class_nme=(TextView) listview.findViewById(R.id.class_name);
            this.course_nme=(TextView) listview.findViewById(R.id.course_name);
            this.stren=(TextView) listview.findViewById(R.id.strength);
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
        holder.class_nme.setText(myclss.getClassName());
        holder.course_nme.setText(myclss.getCourseName());
        holder.stren.setText(myclss.getStrength());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(),myclss.getClassName(),Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return myclass.size();
    }

}
