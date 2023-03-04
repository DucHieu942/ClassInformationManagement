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

import java.util.List;

public class Student_adapter extends RecyclerView.Adapter<Student_adapter.ViewHolder>{


    private android.content.Context context;
    private List<Student> list;



    public Student_adapter(android.content.Context context, List<Student> list){
        this.context = context;
        this.list = list;
        notifyDataSetChanged();
    }

    public void setStudentFilter(List<Student> studentFilter){
        this.list = studentFilter;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public Student_adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.studentcard,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull Student_adapter.ViewHolder holder, int position) {

        Student student = list.get(position);
        holder.fullname.setText(student.getFullname());
        holder.id.setText(String.valueOf(student.getId()));
        holder.absence.setText(String.valueOf(student.getAbsence()));

    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView imageStudent;
        TextView id,fullname,absence;


        public ViewHolder(@NonNull View itemView){
            super(itemView);

            imageStudent = itemView.findViewById(R.id.imageStudent);
            id = itemView.findViewById(R.id.studentId);
            fullname = itemView.findViewById(R.id.fullname);
            absence = itemView.findViewById(R.id.absence);

        }
    }

}
