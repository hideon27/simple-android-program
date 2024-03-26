package com.example.myapplication.ui.loginin;


import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.widget.Toolbar;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.MainActivity;
import com.example.myapplication.R;
import com.example.myapplication.databinding.RegisterBinding;
import com.example.myapplication.mysql.MySQL;
import com.example.myapplication.mysql.MySQLConnection;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Connection;

public class Register extends AppCompatActivity {

    private RegisterBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = RegisterBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        //setContentView(R.layout.register);


        Toolbar toolbar = binding.toolbarRegister;//返回功能

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        Button button_register_confirm = binding.buttonRegisterConfirm;//注册确认
        button_register_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {  //获得sql连接

                new Thread(new Runnable() {
                    @Override
                    public void run() {

                        EditText editText1 = binding.editTextRegisterId;
                        String register_id = editText1.getText().toString();
                        EditText editText2 = binding.editTextRegisterName;
                        String register_name = editText2.getText().toString();
                        EditText editText3 = binding.editTextRegisterPhone;
                        String register_phone = editText3.getText().toString();
                        EditText editText4 = binding.editTextRegisterUserid;
                        String register_userid = editText4.getText().toString();
                        EditText editText5 = binding.editTextRegisterPassword;
                        String register_password = editText5.getText().toString();
                        EditText editText6 = binding.editTextRegisterDescribe;
                        String register_description = editText6.getText().toString();

                        MySQL mySQL = new MySQL();
                        String out = mySQL.insert("employee",register_id,register_name,register_phone,register_userid,register_password,register_description);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(Register.this, out, Toast.LENGTH_SHORT).show();
                            }
                        });
                        mySQL.close_connection();
                    }
                }).start();


            }
        });



    }



}
