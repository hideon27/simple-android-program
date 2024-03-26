package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.myapplication.ui.dashboard.DashboardFragment;
import com.example.myapplication.ui.home.HomeFragment;
import com.example.myapplication.ui.home.HomeViewModel;
import com.example.myapplication.ui.notifications.NotificationsFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.myapplication.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    private String user_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //获得id数据
        Intent intent = getIntent();
        if(intent != null) {user_id = intent.getStringExtra("user_id");}
        //Toast.makeText(this, user_id, Toast.LENGTH_SHORT).show();
        HomeViewModel homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        homeViewModel.setUser_id(user_id);




        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
                .build();
        // 获取 NavController 实例，这里使用 Navigation 类的 findNavController() 方法，
        // 并传入当前 Activity 和 NavHostFragment 的 ID，来获取与 NavHostFragment 关联的 NavController
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);



        // 将 ActionBar 与 NavController 关联起来，以便在导航过程中自动更新 ActionBar 的标题和导航图标
        // 使用 setupActionBarWithNavController() 方法，传入当前 Activity、NavController 和 AppBarConfiguration
        //NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);

        // 将 BottomNavigationView 与 NavController 关联起来，以便在用户点击 BottomNavigationView 中的菜单项时进行导航
        // 使用 setupWithNavController() 方法，传入 BottomNavigationView 组件和 NavController
        NavigationUI.setupWithNavController(binding.navView, navController);



    }

}

