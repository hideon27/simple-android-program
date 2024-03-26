package com.example.myapplication.ui.home;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.Manifest;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.widget.Toolbar;

import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.myapplication.databinding.FragmentHomeBinding;
import com.example.myapplication.databinding.LoginBinding;
import com.example.myapplication.mysql.MySQL;

import java.io.IOException;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding fragmentHomeBinding;
    private String user_id ="1" ;
    private LoginBinding loginBinding;
    private ActivityResultLauncher<Intent> takePictureLauncher;//相机启动器
    private ActivityResultLauncher<String> pickImageLauncher;//图片启动器
    private int REQUEST_CAMERA_PERMISSION=123;//相机请求码


    private Bitmap result_photo;//图片



    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        fragmentHomeBinding = FragmentHomeBinding.inflate(inflater, container, false);
        loginBinding = LoginBinding.inflate(inflater, container, false);

        View root = fragmentHomeBinding.getRoot();


        HomeViewModel homeViewModel = new ViewModelProvider(requireActivity()).get(HomeViewModel.class);
        homeViewModel.getUser_id().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String userId) {
                user_id = userId;
            }
        });


        //拍照启动器初始化
        takePictureLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        // 处理拍照成功后的结果
                        Intent data = result.getData();
                        Bundle extras = data.getExtras();
                        if (extras != null && extras.containsKey("data")) {
                            //Bitmap imageBitmap = (Bitmap) extras.get("data");
                            result_photo = (Bitmap) extras.get("data");
                            // 将拍摄的照片设置到 ImageView 中显示
                            ImageView imageView = fragmentHomeBinding.imageviewFragmentHomePhoto;
                            imageView.setImageBitmap(result_photo);
                        }
                    } else {
                        // 处理拍照失败或用户取消拍照的情况
                    }});

        //图片选择启动器初始化
        pickImageLauncher = registerForActivityResult(
                new ActivityResultContracts.GetContent(),
                result -> {
                    if (result != null) {
                        try {
                            result_photo = MediaStore.Images.Media.getBitmap(requireContext().getContentResolver(), result);
                            ImageView imageView = fragmentHomeBinding.imageviewFragmentHomePhoto;
                            imageView.setImageBitmap(result_photo);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });



        Toolbar toolbar = fragmentHomeBinding.toolbarFragmentHome; //返回功能
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requireActivity().onBackPressed();
            }
        });


        Button button_camera = fragmentHomeBinding.buttonFragmentHomeCamera;//照相按钮
        button_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    // 如果相机权限没有被授予，则请求相机权限
                    ActivityCompat.requestPermissions(requireActivity(), new String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA_PERMISSION);
                }

                if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {

                    Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    if (takePictureIntent.resolveActivity(requireActivity().getPackageManager()) != null) {
                        takePictureLauncher.launch(takePictureIntent);
                    } else {
                        // 处理没有找到相机应用的情况，例如显示一个Toast或者提示用户安装相机应用
                        Toast.makeText(requireContext(), "未找到相机应用", Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    // 当用户拒绝相机权限时显示一个提示消息
                    Toast.makeText(requireContext(), "请前往应用设置页面授予相机权限", Toast.LENGTH_SHORT).show();
                }}});



        Button button_image = fragmentHomeBinding.buttonFragmentHomeImage;//图片按钮
        button_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickImageLauncher.launch("image/*");
            }});


        Button button_submit = fragmentHomeBinding.buttonFragmentHomeSubmit;//上传按钮
        button_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //上传图片到服务器并返回结果


                //增加记录到数据库中
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        //文件名
                        String homeFragment_filename = "ha";
                        //操作者id
                        String homeFragment_uploader_id = user_id;
                        //时间
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        sdf.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
                        String currentTime = sdf.format(new Date());
                        String homeFragment_upload_time = currentTime;
                        //识别结果
                        String homeFragment_result = "3-2";

                        MySQL mySQL = new MySQL();
                        String out = mySQL.insert("upLoadedImages",homeFragment_filename,homeFragment_uploader_id,homeFragment_upload_time,homeFragment_result);
                        requireActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(requireContext(), out, Toast.LENGTH_SHORT).show();
                            }
                        });
                        mySQL.close_connection();
                    }}).start();


            }});



        return root;
    }




    /*
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        final TextView textView = binding.textHome;
        homeViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }
     */



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        fragmentHomeBinding = null;
    }
}