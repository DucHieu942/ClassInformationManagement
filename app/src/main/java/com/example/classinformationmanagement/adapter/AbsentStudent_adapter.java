package com.example.classinformationmanagement.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.classinformationmanagement.R;
import com.example.classinformationmanagement.model.Student;
import com.example.classinformationmanagement.model.StudentManage;

import java.util.List;

public class AbsentStudent_adapter extends RecyclerView.Adapter<AbsentStudent_adapter.ViewHolder> {


    private android.content.Context context;
    private List<StudentManage> list;



    public AbsentStudent_adapter(android.content.Context context, List<StudentManage> list){
        this.context = context;
        this.list = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public AbsentStudent_adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new AbsentStudent_adapter.ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.absentcard,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull AbsentStudent_adapter.ViewHolder holder, int position) {

        StudentManage studentManage = list.get(position);
        holder.fullname.setText(studentManage.getFullname());
        holder.id.setText(String.valueOf(studentManage.getId()));

    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView imageStudent;
        TextView id,fullname;


        public ViewHolder(@NonNull View itemView){
            super(itemView);

            imageStudent = itemView.findViewById(R.id.imageStudent);
            id = itemView.findViewById(R.id.studentId);
            fullname = itemView.findViewById(R.id.fullname);
        }
    }
}
