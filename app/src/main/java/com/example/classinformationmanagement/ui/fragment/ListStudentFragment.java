package com.example.classinformationmanagement.ui.fragment;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.classinformationmanagement.R;
import com.example.classinformationmanagement.adapter.Student_adapter;
import com.example.classinformationmanagement.model.Student;
import com.example.classinformationmanagement.model.StudentManage;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ListStudentFragment extends Fragment {


    private RecyclerView studentRec;
    private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://class-infomation-management-default-rtdb.asia-southeast1.firebasedatabase.app/");
    private List<Student> studentList = new ArrayList<>();
    private Student_adapter student_adapter;
    private SearchView searchView;
    private long absence = 0;
    private Boolean ischeckConnection =false;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_list_student, null);
        getActivity().setTitle("ListStudent");

        searchView = v.findViewById(R.id.searchView);
        searchView.clearFocus();
        studentRec = v.findViewById(R.id.student_list);
        studentRec.setHasFixedSize(true);
        studentRec.setLayoutManager(new LinearLayoutManager(getContext()));
        studentList = new ArrayList<>();

        student_adapter = new Student_adapter(getActivity(),studentList);
        studentRec.setAdapter(student_adapter);


        if(isCheckConnection()){
            databaseReference.child("Student").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {


                    for(DataSnapshot dataSnapshot: snapshot.getChildren()){

                        Student student = dataSnapshot.getValue(Student.class);
                        databaseReference.child("StudentManage").addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                absence=0;
                                for(DataSnapshot studentData:snapshot.getChildren()){
                                    StudentManage studentManageAbsence = studentData.getValue(StudentManage.class);
                                    if(studentManageAbsence.getStudentId().toString().equals(student.getId().toString())
                                            && studentManageAbsence.getRollCall() == false
                                    ){
                                        absence=absence+1;
                                        student.setAbsence(absence);
                                    }

                                }
                                student.setAbsence(absence);
                                studentList.add(student);
                                student_adapter.notifyDataSetChanged();
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });


                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }else {
            Toast.makeText(getActivity(), "No connection", Toast.LENGTH_SHORT).show();
        }

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterList(newText);
                return false;
            }
        });
        return v;
    }



    private void filterList(String newText) {
        List<Student> filteredList = new ArrayList<>();

        //Kiểm tra kí tự nhập vào ô tìm kiếm
        if(newText == null || newText.length() ==0){
            filteredList.addAll(studentList);
        }else {
            String filterPattern = newText.toString().toLowerCase().trim();

            for(Student item: studentList){
                if(item.getFullname().toLowerCase().contains(filterPattern)|| item.getId().toString().contains(filterPattern)){
                    filteredList.add(item);
                }
            }
        }


        if(filteredList.isEmpty()){
            Toast.makeText(getActivity(),"No student found",Toast.LENGTH_SHORT).show();
        }else {
            student_adapter.setStudentFilter(filteredList);
        }
    }


    // Hàm check connection internet
    public boolean isCheckConnection(){

        ConnectivityManager connectivityManager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);

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