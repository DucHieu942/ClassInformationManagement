package com.example.classinformationmanagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.classinformationmanagement.adapter.AbsentStudent_adapter;
import com.example.classinformationmanagement.adapter.StudentManage_adapter;
import com.example.classinformationmanagement.model.Student;
import com.example.classinformationmanagement.model.StudentManage;
import com.example.classinformationmanagement.model.Table;
import com.example.classinformationmanagement.model.Week;
import com.example.classinformationmanagement.ui.fragment.ClassFragment;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AbsentListActivity extends AppCompatActivity {


    private RecyclerView absentStudentRec;
    private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://class-infomation-management-default-rtdb.asia-southeast1.firebasedatabase.app/");
    private List<Student> absentStudentList = new ArrayList<>();
    private AbsentStudent_adapter absentStudent_adapter;
    private Button btnBack;
    Boolean ischeckConnection =false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_absent_list);


        Bundle bundle = getIntent().getExtras();
        if(bundle == null){
            return;
        }
        Week week = (Week) bundle.get("object_week");
        List<Long> listIdStudent = week.getAbsenceStudentIdList();
        btnBack = findViewById(R.id.back2);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });


        absentStudentRec = findViewById(R.id.absentstudent_list);
        absentStudentRec.setHasFixedSize(true);
        absentStudentRec.setLayoutManager(new LinearLayoutManager(this));
        absentStudent_adapter = new AbsentStudent_adapter(this,absentStudentList);
        absentStudentRec.setAdapter(absentStudent_adapter);




        if(isCheckConnection()){
            databaseReference.child("Student").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                        Student student = dataSnapshot.getValue(Student.class);
                        System.out.println("Listid: "+listIdStudent);
                        if(listIdStudent.contains(student.getId())){
                            absentStudentList.add(student);
                        }
                    }
                    absentStudent_adapter.notifyDataSetChanged();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }else {
            Toast.makeText(this, "No connection", Toast.LENGTH_SHORT).show();
        }
    }



    // Hàm check connection internet
    public boolean isCheckConnection(){

        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        if (connectivityManager != null) {
            NetworkCapabilities capabilities = connectivityManager.getNetworkCapabilities(connectivityManager.getActiveNetwork());
            if (capabilities != null) {
                if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) || capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                    ischeckConnection = true;
                } else {




                    ischeckConnection = false;
                }
            }
        }


        return ischeckConnection;
    }
}