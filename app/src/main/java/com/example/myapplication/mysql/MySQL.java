package com.example.myapplication.mysql;

import com.example.myapplication.mysql.data.Employee;
import com.example.myapplication.mysql.data.UploadedImage;
import com.example.myapplication.mysql.function.Insert;
import com.example.myapplication.mysql.function.Query;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class MySQL {

    private Connection connection;

    public MySQL(){connection = new MySQLConnection().getConnection();} //初始化连接

    public void close_connection()  {
        try {
            connection.close();
        } catch (SQLException e) {e.printStackTrace();}}

    public String insert(String table_name,String id,String name,String phone,String userid,String password,String description){//插入数据 员工注册

        Employee employee=new Employee(id,name,phone,userid,password,description);
        Insert insert = new Insert(connection);
        String result  = insert.insert_employee(table_name,employee);
        return result;
    }

    public String insert(String table_name,String filename,String uploader_id,String upload_time,String result){//插入数据 员工操作记录

        UploadedImage uploadedImage=new UploadedImage(filename,uploader_id,upload_time,result);
        Insert insert = new Insert(connection);
        String result1  = insert.insert_upLoadedImages(table_name,uploadedImage);
        return result1;
    }


    public List<Employee> query(String table_name,String id,String name,String phone,String userid,String password,String description){//查询员工表

        Employee employee=new Employee(id,name,phone,userid,password,description);

        Query query = new Query(connection);
        List<Employee> result = query.query(table_name,employee);
        return result;
    }

    public List<UploadedImage> query(String table_name,String filename,String uploader_id,String upload_time,String result){//查询操作表

        UploadedImage uploadedImage = new UploadedImage(filename,uploader_id,upload_time,result);

        Query query = new Query(connection);
        List<UploadedImage> result1 = query.query(table_name,uploadedImage);
        return result1;
    }





}
