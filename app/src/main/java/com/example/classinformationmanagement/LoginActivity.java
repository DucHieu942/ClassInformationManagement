package com.example.classinformationmanagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {

    TextInputEditText userName, passWord;
    TextInputLayout userNameLayout, passWordLayout;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://class-infomation-management-default-rtdb.asia-southeast1.firebasedatabase.app/");
    FirebaseDatabase firebaseDatabase;
    String userNameText, passWordText;
    String userNamePattern = "[a-z]";
    Context activity = LoginActivity.this;
    Boolean isvalid = false,
            isvalidUserName = false,
            isvalidPassWord = false,
            ischeckLogin = false,
            ischeckConnection =false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        try {
            userName = (TextInputEditText) findViewById(R.id.userNameInput);
            passWord = (TextInputEditText) findViewById(R.id.password);
            Button buttonLogin = (Button) findViewById(R.id.buttonLogin);

            buttonLogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    userNameText = userName.getText().toString().trim();
                    passWordText = passWord.getText().toString().trim();


                   if(isCheckConnection()){
                       if (isValid()) {
                           final ProgressDialog mDialog = new ProgressDialog(activity);
                           mDialog.setCanceledOnTouchOutside(false);
                           mDialog.setCancelable(false);
                           mDialog.setMessage("Sign In Please Wait.....");
                           mDialog.show();
                           //Check thông tin xem đúng không
                           isCheckLogin(mDialog);
                       }
                   }else {
                       Toast.makeText(activity, "No connection", Toast.LENGTH_SHORT).show();
                   }


                }
            });
        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG);
        }

    }

    // Hàm check Valid userName and Password
    public boolean isValid() {
        // Check valid username and password
        if (TextUtils.isEmpty(userNameText) && TextUtils.isEmpty(passWordText)) {
            Toast.makeText(activity, "Please enter infor", Toast.LENGTH_SHORT).show();
            return isvalid;
        }
        // Check valid username
        if (TextUtils.isEmpty(userNameText)) {
            Toast.makeText(activity, "Please enter username", Toast.LENGTH_SHORT).show();

        } else {
            isvalidUserName = true;
        }
        // Check valid password
        if (TextUtils.isEmpty(passWordText)) {
            Toast.makeText(activity, "Please enter password", Toast.LENGTH_SHORT).show();
        } else {
            isvalidPassWord = true;
        }
        isvalid = (isvalidUserName && isvalidPassWord) ? true : false;
        return isvalid;
    }


    public void isCheckLogin(ProgressDialog mDialog) {
        //check data to firebase Realtime Database,
        //Check theo userName
        databaseReference.child("User").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // Check username in firebase
                if (snapshot.hasChild(userNameText)) {
                    final String getPassWord = snapshot.child(userNameText).child("password").getValue(String.class);
                    if (!getPassWord.equals(passWordText)) {
                        Toast.makeText(activity, "Wrong Password", Toast.LENGTH_SHORT).show();
                        mDialog.cancel();
                    } else {
                        ischeckLogin = true;
                        String fullname = snapshot.child(userNameText).child("fullname").getValue(String.class);
                        String imgUrl = snapshot.child(userNameText).child("imgUrl").getValue(String.class);
                        Toast.makeText(activity, "Successful Login", Toast.LENGTH_SHORT).show();
                        mDialog.cancel();
                        finish();
                        Intent intent = new Intent(activity, HomeActivity.class);
                        intent.putExtra("UserLogin", userNameText);
                        intent.putExtra("FullName",fullname);
                        intent.putExtra("ImgUrl",imgUrl);
                        startActivity(intent);
                    }
                } else {
                    Toast.makeText(activity, "Wrong UserName", Toast.LENGTH_SHORT).show();
                    mDialog.cancel();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(activity, "Lỗi ở đây này", Toast.LENGTH_LONG).show();
            }
        });
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