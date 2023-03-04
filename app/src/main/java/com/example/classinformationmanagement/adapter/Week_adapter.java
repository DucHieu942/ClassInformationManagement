package com.example.classinformationmanagement.adapter;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.classinformationmanagement.AbsentListActivity;
import com.example.classinformationmanagement.R;
import com.example.classinformationmanagement.RollCallActivity;
import com.example.classinformationmanagement.model.Student;
import com.example.classinformationmanagement.model.Table;
import com.example.classinformationmanagement.model.Week;

import java.util.List;

public class Week_adapter extends RecyclerView.Adapter<Week_adapter.ViewHolder>{


    private android.content.Context context;
    private List<Week> list;



    public Week_adapter(android.content.Context context, List<Week> list){
        this.context = context;
        this.list = list;
        notifyDataSetChanged();
    }

    public void setWeekFilter(List<Week> weekFilter){
        this.list = weekFilter;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public Week_adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Week_adapter.ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.weekcard,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull Week_adapter.ViewHolder holder, int position) {

        Week week = list.get(position);
        holder.name.setText(week.getName());
        holder.date.setText(String.valueOf(week.getDate()));
//        holder.quantity.setText(String.valueOf(week.getQuantity()));
        holder.quantity.setText(String.valueOf(week.getQuantity()));
        if(week.getAbsenceStudentIdList() == null){
            return;
        }else {
            holder.cardViewWeek.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickItem(week);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView quantity,name,date;
        CardView cardViewWeek;


        public ViewHolder(@NonNull View itemView){
            super(itemView);


            cardViewWeek = itemView.findViewById(R.id.week_item);
            name = itemView.findViewById(R.id.week);
            date = itemView.findViewById(R.id.date);
            quantity= itemView.findViewById(R.id.quantity);

        }
    }

    public void onClickItem(Week week){
        Intent intent = new Intent(context, AbsentListActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("object_week",week);
        intent.putExtras(bundle);
        context.startActivity(intent);

    }
}
