package com.example.myapplication.ui.dashboard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.databinding.FragmentDashboardBinding;
import com.example.myapplication.databinding.LoginBinding;
import com.example.myapplication.mysql.MySQL;
import com.example.myapplication.mysql.data.UploadedImage;
import com.example.myapplication.ui.home.HomeViewModel;
import com.example.myapplication.ui.loginin.Register;

import java.util.ArrayList;
import java.util.List;


public class DashboardFragment extends Fragment {// 声明一个 FragmentHomeBinding 对象，用于绑定 Fragment 的布局文件

    //LayoutInflater inflater：布局解析器，用于将 XML 布局文件解析为对应的 View 对象。
    //ViewGroup container：父容器，用于指定 Fragment 的布局应该添加到哪个父容器中。
    //Bundle savedInstanceState：保存 Fragment 状态信息的 Bundle 对象。

    private FragmentDashboardBinding fragmentDashboardBinding;
    private String user_id;
    private RecyclerView recyclerView;
    private UploadedImageAdapter adapter;
    private List<UploadedImage> resultList; //查询结果list



    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            user_id = getArguments().getString("user_id");
        }
    }


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        DashboardViewModel dashboardViewModel =
                new ViewModelProvider(this).get(DashboardViewModel.class);
        fragmentDashboardBinding = FragmentDashboardBinding.inflate(inflater, container, false);
        View root = fragmentDashboardBinding.getRoot();

        HomeViewModel homeViewModel = new ViewModelProvider(requireActivity()).get(HomeViewModel.class);
        homeViewModel.getUser_id().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String userId) {
                user_id = userId;
            }
        });




        Toolbar toolbar = fragmentDashboardBinding.toolbarFragmentDashboard; //返回功能
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requireActivity().onBackPressed();
            }
        });


        recyclerView = fragmentDashboardBinding.recyclerviewFragmentDashboard;//列表显示
        // 创建布局管理器
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(requireContext());
        recyclerView.setLayoutManager(layoutManager);

        adapter = new UploadedImageAdapter(new ArrayList<UploadedImage>());
        recyclerView.setAdapter(adapter);

        new Thread(new Runnable() {
            @Override
            public void run() {
                // 获取查询结果列表
                MySQL mySQL = new MySQL();
                resultList = mySQL.query("upLoadedImages", null,user_id,null,null); // 根据实际情况获取查询结果
                mySQL.close_connection();

                // 在主线程中设置 RecyclerView 的适配器
                requireActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        // 创建适配器并设置给 RecyclerView
                        adapter.setData(resultList);
                        //Toast.makeText(requireContext(), "out", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }).start();



        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        fragmentDashboardBinding = null;
    }
}