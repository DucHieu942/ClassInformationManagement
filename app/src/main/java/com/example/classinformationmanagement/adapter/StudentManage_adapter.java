package com.example.classinformationmanagement.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.classinformationmanagement.R;
import com.example.classinformationmanagement.model.Student;
import com.example.classinformationmanagement.model.StudentManage;
import com.example.classinformationmanagement.model.Table;

import java.util.List;

public class StudentManage_adapter extends RecyclerView.Adapter<StudentManage_adapter.ViewHolder>{
    private android.content.Context context;
    private List<StudentManage> list;
    private IListenerClickCheckBox iListenerClickCheckBox;
    private IListenerSendEmail iListenerSendEmail;
    private IListenerCall iListenerCall;
    private IListenerSendMgs iListenerSendMgs;



    public StudentManage_adapter(android.content.Context context, List<StudentManage> list,
                                 IListenerClickCheckBox iListenerClickCheckBox,
                                 IListenerSendEmail iListenerSendEmail,
                                 IListenerCall iListenerCall,
                                 IListenerSendMgs iListenerSendMgs){
        this.context = context;
        this.list = list;
        this.iListenerClickCheckBox=iListenerClickCheckBox;
        this.iListenerSendEmail=iListenerSendEmail;
        this.iListenerCall=iListenerCall;
        this.iListenerSendMgs=iListenerSendMgs;
        notifyDataSetChanged();
    }

    public interface IListenerClickCheckBox {
        void onClickCheckBox(StudentManage studentManage,int position);
    }

    public interface IListenerSendEmail {
        void onClickSendEmail(StudentManage studentManage,int position);
    }


    public interface IListenerCall {
        void onClickCall(StudentManage studentManage,int position);
    }

    public interface IListenerSendMgs {
        void onClickSendMgs(StudentManage studentManage,int position);
    }



    @NonNull
    @Override
    public StudentManage_adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new StudentManage_adapter.ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.studentmanagecard,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull StudentManage_adapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        StudentManage studentManage = list.get(position);
        if(studentManage == null){
            return;
        }
        holder.fullname.setText(studentManage.getFullname());
        holder.id.setText(String.valueOf(studentManage.getStudentId()));
        holder.isRollCall.setChecked(studentManage.getRollCall());

        holder.isRollCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iListenerClickCheckBox.onClickCheckBox(studentManage,position);
            }
        });

        holder.sendEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iListenerSendEmail.onClickSendEmail(studentManage,position);
            }
        });

        holder.call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iListenerCall.onClickCall(studentManage,position);
            }
        });

        holder.sendMsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iListenerSendMgs.onClickSendMgs(studentManage,position);
            }
        });



    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView imageStudent;
        CardView rollcallcard;
        CheckBox isRollCall;
        TextView id,fullname;
        ImageView sendEmail;
        ImageView call;
        ImageView sendMsg;


        public ViewHolder(@NonNull View itemView){
            super(itemView);

            imageStudent = itemView.findViewById(R.id.imageStudent);
            id = itemView.findViewById(R.id.studentId);
            fullname = itemView.findViewById(R.id.fullname);
            isRollCall = itemView.findViewById(R.id.rollcall);
            rollcallcard = itemView.findViewById(R.id.rollcallcard);
            sendEmail = itemView.findViewById(R.id.send_email);
            call = itemView.findViewById(R.id.call);
            sendMsg = itemView.findViewById(R.id.send_message);

        }
    }
}
