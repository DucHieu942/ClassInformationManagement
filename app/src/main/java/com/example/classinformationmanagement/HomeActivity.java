package com.example.classinformationmanagement;

import androidx.appcompat.app.AppCompatActivity;
import androidx.collection.CircularArray;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.classinformationmanagement.databinding.ActivityHomeBinding;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.squareup.picasso.Picasso;

public class HomeActivity extends AppCompatActivity {


    private AppBarConfiguration mAppBarConfiguration;
    private ActivityHomeBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        String userNameLogin = getIntent().getStringExtra("UserLogin");
        String fullName = getIntent().getStringExtra("FullName");
        String imgUrl = getIntent().getStringExtra("ImgUrl");



        setSupportActionBar(binding.appBarMain.toolbar);


        DrawerLayout drawer = binding.getRoot().findViewById(R.id.drawer_layout);
        NavigationView navigationView = drawer.findViewById(R.id.nav_view);
        View headerlayout = navigationView.getHeaderView(0);
        TextView username = headerlayout.findViewById(R.id.username);
        TextView fullname = headerlayout.findViewById(R.id.fullname);
        ImageView imageView = headerlayout.findViewById(R.id.imageView);

        username.setText(userNameLogin);
        fullname.setText(fullName);
        Picasso.get().load(imgUrl).resize(136,136).into(imageView);







        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.classdiagram, R.id.liststudent, R.id.week,R.id.informationapp)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }





    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}