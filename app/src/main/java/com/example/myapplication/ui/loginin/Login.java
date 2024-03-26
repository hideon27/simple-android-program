package com.example.myapplication.ui.loginin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.MainActivity;
import com.example.myapplication.R;
import com.example.myapplication.mysql.MySQL;
import com.example.myapplication.mysql.data.Employee;
import com.example.myapplication.mysql.data.UploadedImage;

import java.util.List;

public class Login extends AppCompatActivity {

    private Boolean login_inform;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);




        // 获取按钮对象
        Button button_login = findViewById(R.id.button_login_login);
        // 设置按钮点击事件监听器
        button_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login_inform=false;

                new Thread(new Runnable() {
                    @Override
                    public void run() {

                        EditText editText1 = findViewById(R.id.editText_login_userid);
                        String login_userid = editText1.getText().toString();
                        EditText editText2 = findViewById(R.id.editText_login_password);
                        String login_password = editText2.getText().toString();

                        MySQL mySQL = new MySQL();
                        List<Employee> query_result = mySQL.query("employee",null,null,null,login_userid,login_password,null);
                        //List<Employee> query_result = mySQL.query("employee",null,null,null,null,null,null);

                        for (Employee employee : query_result) {
                            if (employee.getUserId().equals(login_userid) && employee.getPassword().equals(login_password)) {
                                // 找到匹配的用户，退出循环
                                login_inform = true;
                                break;
                            }
                        }

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if(login_inform) {
                                    Toast.makeText(Login.this, "成功", Toast.LENGTH_SHORT).show();
                                    //Toast.makeText(Login.this, login_userid, Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(Login.this, MainActivity.class);
                                    intent.putExtra("user_id", login_userid);
                                    startActivity(intent);
                                }
                                else {Toast.makeText(Login.this, "失败", Toast.LENGTH_SHORT).show();}
                            }
                        });


                        mySQL.close_connection();
                    }
                }).start();



            }
        });

        Button button_register = findViewById(R.id.button_login_register);
        button_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login.this, Register.class);
                intent.putExtra("key", "value");
                startActivity(intent);
            }
        });


    }


}
