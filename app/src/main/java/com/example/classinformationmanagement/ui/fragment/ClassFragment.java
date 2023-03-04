package com.example.classinformationmanagement.ui.fragment;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.classinformationmanagement.R;
import com.example.classinformationmanagement.adapter.Student_adapter;
import com.example.classinformationmanagement.adapter.Table_adapter;
import com.example.classinformationmanagement.model.Student;
import com.example.classinformationmanagement.model.StudentManage;
import com.example.classinformationmanagement.model.Table;
import com.google.android.gms.common.util.ScopeUtil;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;


public class ClassFragment extends Fragment {

    private RecyclerView tableRec;
    private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://class-infomation-management-default-rtdb.asia-southeast1.firebasedatabase.app/");
    private List<Table> tableList = new ArrayList<>();
    private Table_adapter table_adapter;
    public static Integer idWeek = 6;
    private TextView weekName,dayOfWeekView;
    private Spinner spinnerWeek;
    private Boolean ischeckConnection =false;
    private static final int REQUEST_CALL_PHONE_PERMISSION = 1;
    public static boolean isFunctionCalled = false;
    private Integer[] weeks = new Integer[]{1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21};
    private String[] dayOfWeek = new String[]{"11/10","18/10","25/10","1/11"
            ,"8/11","15/11","22/11","29/11",
             "6/12","13/12","20/12","27/12",
            "3/1","10/1","17/1","24/1","31/1"
            ,"7/2","14/2","21/2"}    ;
    private int countStudent = 0;
    private  TextView countStudentView;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment




        View v = inflater.inflate(R.layout.fragment_class, null);
        getActivity().setTitle("Class");


        countStudentView = v.findViewById(R.id.countStudent);
        dayOfWeekView = v.findViewById(R.id.dateView);


        spinnerWeek = v.findViewById(R.id.spinner_week);
//        weekName = v.findViewById(R.id.weekId);

        ArrayAdapter<Integer> adapter = new ArrayAdapter<Integer>(getActivity(), android.R.layout.simple_spinner_dropdown_item, weeks);
        spinnerWeek.setAdapter(adapter);




        spinnerWeek.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedWeek = parent.getItemAtPosition(position).toString();
                idWeek = Integer.parseInt(selectedWeek);
                dayOfWeekView.setText("Ngày: "+dayOfWeek[position]);

                if(isCheckConnection()){
                    tableRec = v.findViewById(R.id.table_list);
                    tableRec.setHasFixedSize(true);
                    GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(),2);
                    tableRec.setLayoutManager(gridLayoutManager);
                    tableList = new ArrayList<>();
                    table_adapter = new Table_adapter(getActivity(),tableList);
                    tableRec.setAdapter(table_adapter);
                    getData();


                } else {
                    Toast.makeText(getActivity(), "No connection", Toast.LENGTH_SHORT).show();
                }



            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing
            }
        });



        return v;
    }


    // Thực hiện các tác vụ khởi tạo lại Fragment
    @Override
    public void onResume() {
        super.onResume();
        if (isFunctionCalled) {
            table_adapter.clearData();
            getData();
            isFunctionCalled = false;
            countStudent = 0;
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

// Hàm lấy data
    public  void getData(){
        databaseReference.child("Table").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {



                countStudent = 0;
                for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                    List<String> listNameStudent = new ArrayList<>();
                    List<String> listRoleStudent = new ArrayList<>();
                    List<Long> listIdStudent = new ArrayList<>();
                    List<Boolean> listRollCall = new ArrayList<>();
                    Table table = dataSnapshot.getValue(Table.class);
                    databaseReference.child("StudentManage").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                            for(DataSnapshot dataSnapshot: snapshot.getChildren()){


                                StudentManage studentManage = dataSnapshot.getValue(StudentManage.class);
                                if(studentManage.getGroupId() == table.getId() && studentManage.getWeekId() == idWeek.longValue()){
                                    listRoleStudent.add(studentManage.getRole());
                                    listNameStudent.add(studentManage.getName());
                                    listIdStudent.add(studentManage.getId());
                                    listRollCall.add(studentManage.getRollCall());
                                    if(studentManage.getRollCall() == true){
                                        countStudent = countStudent +1;
                                        countStudentView.setText("Sĩ số: "+countStudent +"/"+"27");
                                    }
                                }



                            }
                            table.setListRollCall(listRollCall);
                            table.setListrole(listRoleStudent);
                            table.setListname(listNameStudent);
                            table.setListid(listIdStudent);
                            tableList.add(table);
                            table_adapter.notifyDataSetChanged();
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
    }



}