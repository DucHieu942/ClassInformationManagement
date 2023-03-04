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
import com.example.classinformationmanagement.adapter.Week_adapter;
import com.example.classinformationmanagement.model.Student;
import com.example.classinformationmanagement.model.StudentManage;
import com.example.classinformationmanagement.model.Week;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class WeekFragment extends Fragment {


    private RecyclerView weekRec;
    private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://class-infomation-management-default-rtdb.asia-southeast1.firebasedatabase.app/");
    private List<Week> weekList = new ArrayList<>();
    private Week_adapter week_adapter;
    private SearchView searchView;
    private Integer quantity = 0;
    private Integer quantityAll = 27;
    private List<Long> absentStudentIdList = new ArrayList<>();
    private Boolean ischeckConnection =false;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment



        View v = inflater.inflate(R.layout.fragment_week, null);
        getActivity().setTitle("Week");

        searchView = v.findViewById(R.id.searchView);
        searchView.clearFocus();
        weekRec = v.findViewById(R.id.week_list);
        weekRec.setHasFixedSize(true);
        weekRec.setLayoutManager(new LinearLayoutManager(getContext()));
        weekList = new ArrayList<>();

        week_adapter = new Week_adapter(getActivity(),weekList);
        weekRec.setAdapter(week_adapter);




// Call api lấy danh sách các Tuần học


        if(isCheckConnection()){
            databaseReference.child("Week").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {


                    for(DataSnapshot dataSnapshot: snapshot.getChildren()){

                        Week week = dataSnapshot.getValue(Week.class);

// Check số lượng sinh viên đi học mỗi tuần
                        databaseReference.child("StudentManage").addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                quantity=0;
                                for(DataSnapshot studentData:snapshot.getChildren()){
                                    StudentManage studentManageAbsence = studentData.getValue(StudentManage.class);
                                    if(studentManageAbsence.getWeekId().toString().equals(week.getId().toString())
                                            && studentManageAbsence.getRollCall() == false
                                    ){

                                        absentStudentIdList.add(studentManageAbsence.getId());
                                        quantity=quantity+1;
                                        week.setAbsenceStudentIdList(absentStudentIdList);
                                        week.setQuantity(quantityAll-quantity);
                                    }

                                }
                                week.setQuantity(quantityAll-quantity);
                                weekList.add(week);
                                week_adapter.notifyDataSetChanged();
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });


//                    weekList.add(week);
                    }
                    week_adapter.notifyDataSetChanged();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        } else {
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





    //Hàm tìm kiếm theo tên Tuần ví dụ Input: Tuần 1, Output: Hiển thị ra danh sách chỉ chưa Tuần 1
    private void filterList(String newText) {
        List<Week> filteredList = new ArrayList<>();

        //Kiểm tra kí tự nhập vào ô tìm kiếm
        if(newText == null || newText.length() ==0){
            filteredList.addAll(weekList);
        }else {
            String filterPattern = newText.toString().toLowerCase().trim();

            for(Week item: weekList){
                if(item.getName().toLowerCase().contains(filterPattern)|| item.getId().toString().contains(filterPattern)){
                    filteredList.add(item);
                }
            }
        }


        if(filteredList.isEmpty()){
            Toast.makeText(getActivity(),"No week found",Toast.LENGTH_SHORT).show();
        }else {
            week_adapter.setWeekFilter(filteredList);
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