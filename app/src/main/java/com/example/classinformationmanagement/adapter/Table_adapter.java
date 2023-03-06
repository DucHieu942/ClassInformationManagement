package com.example.classinformationmanagement.adapter;

import android.content.Intent;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.classinformationmanagement.R;
import com.example.classinformationmanagement.RollCallActivity;
import com.example.classinformationmanagement.model.Student;
import com.example.classinformationmanagement.model.Table;
import com.example.classinformationmanagement.model.Week;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Table_adapter extends RecyclerView.Adapter<Table_adapter.ViewHolder> {


    private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://class-infomation-management-default-rtdb.asia-southeast1.firebasedatabase.app/");

    private android.content.Context context;
    private List<Table> list;

    public Table_adapter(android.content.Context context, List<Table> list){
        this.context = context;
        this.list = list;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public Table_adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Table_adapter.ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.tablecard,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull Table_adapter.ViewHolder holder, int position) {
        Table table = list.get(position);
        holder.group.setText(table.getName());
        if(table.getListname() !=null){
            if(table.getListname().size()==3){
                holder.firstStudent.setText(table.getListname().get(0));
                holder.secondStudent.setText(table.getListname().get(1));
                holder.thirdStudent.setText(table.getListname().get(2));
            }
            if(table.getListname().size()==2){
                holder.firstStudent.setText(table.getListname().get(0));
                holder.secondStudent.setText(table.getListname().get(1));
                holder.thirdStudent.setText(".......");
            }
        }


        if(table.getListrole() !=null){
            if(table.getListrole().size()==3){
                if(table.getListrole().get(0).equals("leader")){
                    holder.firstStudent.setTypeface(null, Typeface.BOLD_ITALIC);
                    holder.firstStudent.setPaintFlags(holder.firstStudent.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
                }
                else {
                    holder.firstStudent.setTypeface(null, Typeface.NORMAL);
                    holder.firstStudent.setPaintFlags(holder.firstStudent.getPaintFlags() & (~Paint.UNDERLINE_TEXT_FLAG));
                }
                if(table.getListrole().get(1).equals("leader")){
                    holder.secondStudent.setTypeface(null, Typeface.BOLD_ITALIC);
                    holder.secondStudent.setPaintFlags(holder.secondStudent.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
                }
                else {
                    holder.secondStudent.setTypeface(null, Typeface.NORMAL);
                    holder.secondStudent.setPaintFlags(holder.secondStudent.getPaintFlags() & (~Paint.UNDERLINE_TEXT_FLAG));
                }
                if(table.getListrole().get(2).equals("leader")){
                    holder.thirdStudent.setTypeface(null, Typeface.BOLD_ITALIC);
                    holder.thirdStudent.setPaintFlags(holder.thirdStudent.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
                }
                else {
                    holder.thirdStudent.setTypeface(null, Typeface.NORMAL);
                    holder.thirdStudent.setPaintFlags(holder.thirdStudent.getPaintFlags() & (~Paint.UNDERLINE_TEXT_FLAG));
                }
            }
            if(table.getListname().size()==2){
                if(table.getListrole().get(0).equals("leader")){
                    holder.firstStudent.setTypeface(null, Typeface.BOLD_ITALIC);
                    holder.firstStudent.setPaintFlags(holder.firstStudent.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
                }
                else {
                    holder.firstStudent.setTypeface(null, Typeface.NORMAL);
                    holder.firstStudent.setPaintFlags(holder.firstStudent.getPaintFlags() & (~Paint.UNDERLINE_TEXT_FLAG));
                }
                if(table.getListrole().get(1).equals("leader")){
                    holder.secondStudent.setTypeface(null, Typeface.BOLD_ITALIC);
                    holder.secondStudent.setPaintFlags(holder.secondStudent.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
                }
                else {
                    holder.secondStudent.setTypeface(null, Typeface.NORMAL);
                    holder.secondStudent.setPaintFlags(holder.secondStudent.getPaintFlags() & (~Paint.UNDERLINE_TEXT_FLAG));
                }
            }

        }



        if(table.getListRollCall() !=null){
            if(table.getListRollCall().size()==3){
                if(table.getListRollCall().get(0) == true){
                    holder.firstStudent.setBackgroundResource(R.drawable.green_bg);
                }
                else {
                    holder.firstStudent.setBackgroundResource(R.drawable.red_bg);
                }
                if(table.getListRollCall().get(1)== true){
                    holder.secondStudent.setBackgroundResource(R.drawable.green_bg);
                }
                else {
                    holder.secondStudent.setBackgroundResource(R.drawable.red_bg);
                }
                if(table.getListRollCall().get(2) == true){
                    holder.thirdStudent.setBackgroundResource(R.drawable.green_bg);
                }
                else {
                    holder.thirdStudent.setBackgroundResource(R.drawable.red_bg);
                }
            }
            if(table.getListRollCall().size()==2){
                if(table.getListRollCall().get(0) == true){
                    holder.firstStudent.setBackgroundResource(R.drawable.green_bg);
                }
                else {
                    holder.firstStudent.setBackgroundResource(R.drawable.red_bg);
                }
                if(table.getListRollCall().get(1) == true){
                    holder.secondStudent.setBackgroundResource(R.drawable.green_bg);
                }
                else {
                    holder.secondStudent.setBackgroundResource(R.drawable.red_bg);
                }
            }

        }


        holder.cardViewTable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickItem(table);
            }
        });

    }


    @Override
    public int getItemCount() {
        return list.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView group,firstStudent,secondStudent,thirdStudent;
        CardView cardViewTable ;


        public ViewHolder(@NonNull View itemView){
            super(itemView);

            cardViewTable = itemView.findViewById(R.id.table_item);
            group = itemView.findViewById(R.id.group);
            firstStudent = itemView.findViewById(R.id.nameStudent1);
            secondStudent= itemView.findViewById(R.id.nameStudent2);
            thirdStudent = itemView.findViewById(R.id.nameStudent3);

        }
    }

    public void onClickItem(Table table){
        Intent intent = new Intent(context, RollCallActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("object_table",table);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    public List<Table> getList() {
        return list;
    }

    public void setList(List<Table> list) {
        this.list = list;
    }


    public void clearData() {
        list.clear();
        notifyDataSetChanged();
    }
}
