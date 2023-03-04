package com.example.classinformationmanagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;
import android.net.Uri;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import com.example.classinformationmanagement.adapter.StudentManage_adapter;
import com.example.classinformationmanagement.model.Student;
import com.example.classinformationmanagement.model.StudentManage;
import com.example.classinformationmanagement.model.Table;
import com.example.classinformationmanagement.ui.fragment.ClassFragment;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class RollCallActivity extends AppCompatActivity {


    private RecyclerView studentManageRec;
    private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://class-infomation-management-default-rtdb.asia-southeast1.firebasedatabase.app/");
    private List<StudentManage> studentManageList = new ArrayList<>();
    private StudentManage_adapter studentManage_adapter;
    private Button btnBack;
    private  long maxid =0;
    private  static long idWeek = 12;
    private TextView groupName;
    Boolean isRollCallSelect;
    Boolean ischeckConnection =false;




    @SuppressLint("MissingInflatedId")



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_roll_call);

        Bundle bundle = getIntent().getExtras();
        if(bundle == null){
            return;
        }
        Table table = (Table) bundle.get("object_table");



        idWeek = ClassFragment.idWeek;
        groupName = findViewById(R.id.groupId);
        groupName.setText(table.getName());
        List<Long> listIdStudent = table.getListid();
        btnBack = findViewById(R.id.back);
        studentManageRec = findViewById(R.id.rollcall_list);
        studentManageRec.setHasFixedSize(true);
        studentManageRec.setLayoutManager(new LinearLayoutManager(this));
        studentManageList = new ArrayList<>();

        studentManage_adapter = new StudentManage_adapter(this, studentManageList, new StudentManage_adapter.IListenerClickCheckBox() {
            @Override
            public void onClickCheckBox(StudentManage studentManage, int position) {

                Long idStudentSelect =studentManage.getStudentId();
                Long idWeekSelect = studentManage.getWeekId();

                if(isCheckConnection()){
                    databaseReference.child("StudentManage")
                            .child(String.valueOf(idStudentSelect)+"-"+String.valueOf(idWeekSelect))
                            .child("rollCall").addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    isRollCallSelect = snapshot.getValue(Boolean.class);
                                    databaseReference.child("StudentManage")
                                            .child(String.valueOf(idStudentSelect)+"-"+String.valueOf(idWeekSelect))
                                            .child("rollCall").setValue(!isRollCallSelect);
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });
                }
            }
        },new StudentManage_adapter.IListenerSendEmail(){

            @Override
            public void onClickSendEmail(StudentManage studentManage, int position) {
                String email = studentManage.getEmail();

                System.out.println("Email: "+email);

                Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
                emailIntent.setData(Uri.parse("mailto:" + email));

                if (emailIntent.resolveActivity(getPackageManager()) != null) {
                    startActivity(emailIntent);
                }
            }
        },new StudentManage_adapter.IListenerCall(){

            @Override
            public void onClickCall(StudentManage studentManage, int position) {

                String phone = studentManage.getPhone();

//                Intent callIntent = new Intent(Intent.ACTION_DIAL);
//                callIntent.setData(Uri.parse("tel:" + phone));
//                System.out.println("có chạy vào hàm Call");
//
//                if (callIntent.resolveActivity(getPackageManager()) != null) {
//                    startActivity(callIntent);
//                }
//                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phone));
//                startActivity(intent);

                Intent dialIntent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phone));
                startActivity(dialIntent);
            }
        },new StudentManage_adapter.IListenerSendMgs(){

            @Override
            public void onClickSendMgs(StudentManage studentManage, int position) {
                String phone = studentManage.getPhone();
                Uri smsUri = Uri.parse("smsto:" + phone);
                Intent messageIntent = new Intent(Intent.ACTION_SENDTO, smsUri); // Tạo Intent với hành động gửi tin nhắn và Uri
                messageIntent.putExtra("sms_body", "Lí do vắng mặt hôm nay là gì vậy em"); // Nếu muốn điền sẵn nội dung tin nhắn
                startActivity(messageIntent);
            }
        });
        studentManageRec.setAdapter(studentManage_adapter);



        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClassFragment.isFunctionCalled = true;
                finish();
            }
        });

        //Create StudentManage

        for(Long id:listIdStudent){
            if(isCheckConnection()){
                databaseReference.child("StudentManage").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        StudentManage studentManageCreate = new StudentManage();
                        studentManageCreate.setId(id);
                        studentManageCreate.setStudentId(id);
                        studentManageCreate.setRollCall(false);
                        studentManageCreate.setWeekId(idWeek);
                        studentManageCreate.setGroupId(table.getId());
                        studentManageCreate.setEmail("hieu.dhd182508@sis.hust.edu.vn");
                        studentManageCreate.setPhone("0394567942");
                        studentManageCreate.setRole("member");
                        studentManageCreate.setName("Long");

                        String key = String.valueOf(id)+"-"+String.valueOf(idWeek);

                        if(!snapshot.exists()){
                            databaseReference.child("StudentManage").child(key).setValue(studentManageCreate);
                        }else {
                            for(DataSnapshot dataSnapshot:snapshot.getChildren()){
                                StudentManage studentManage= dataSnapshot.getValue(StudentManage.class);
                                if(dataSnapshot.getKey().toString().equals(key)){
                                    databaseReference.child("StudentManage").child(key).child("rollCall").setValue(studentManage.getRollCall());
//                                    databaseReference.child("StudentManage").child(key).child("email").setValue(studentManageCreate.getEmail());
//                                    databaseReference.child("StudentManage").child(key).child("phone").setValue(studentManageCreate.getPhone());
                                    break;
                                }
//                                else {
//                                    databaseReference.child("StudentManage").child(key).setValue(studentManageCreate);
//                                }
                            }

                        }



                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
            else {
                Toast.makeText(this, "No connection", Toast.LENGTH_SHORT).show();
            }
        }

         //Get StudentManage

        if(isCheckConnection()){
            databaseReference.child("StudentManage").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                        StudentManage studentManage = dataSnapshot.getValue(StudentManage.class);
                        if(studentManage.getGroupId().toString().equals(table.getId().toString())
                                &&listIdStudent.contains(studentManage.getStudentId())
                                &&studentManage.getWeekId()== idWeek
                        ){
                            databaseReference.child("Student").addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                                        Student student= dataSnapshot.getValue(Student.class);
                                        if(student.getId().toString().equals(studentManage.getStudentId().toString())){
                                            studentManage.setFullname(student.getFullname());
                                            studentManageList.add(studentManage);
                                            studentManage_adapter.notifyDataSetChanged();
                                        }
                                    }

                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });

                        }
                    }

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


//    @Override
//    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        if (requestCode == REQUEST_CALL_PHONE_PERMISSION) {
//            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                // Quyền truy cập đã được cấp, bạn có thể thực hiện cuộc gọi ở đây
//            } else {
//                // Quyền truy cập bị từ chối, bạn cần thông báo cho người dùng
//            }
//        }
//    }



}