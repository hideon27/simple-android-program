package com.example.myapplication.mysql.function;

import com.example.myapplication.mysql.data.Employee;
import com.example.myapplication.mysql.data.UploadedImage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class Insert {
    private Connection connection;
    private Logger logger;

    public Insert(Connection connection) {
        this.connection = connection;
        this.logger = Logger.getLogger(Query.class.getName());
    }


    public String insert_employee(String tableName, Employee conditions) {

        String result = "注册成功";

        String sql = "INSERT INTO " + tableName + " (id, name, phone, userid, password, description) VALUES (?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, conditions.getId());
            preparedStatement.setString(2, conditions.getName());
            preparedStatement.setString(3, conditions.getPhone());
            preparedStatement.setString(4, conditions.getUserId());
            preparedStatement.setString(5, conditions.getPassword());
            preparedStatement.setString(6, conditions.getDescription());

            preparedStatement.executeUpdate();
            preparedStatement.close();

        } catch (SQLException e) {
            logger.severe("Error executing query: " + e.getMessage());
            result = e.getMessage();
        }
        return result; //返回的是插入是否成功的提示信息
    }

    public String insert_upLoadedImages(String tableName, UploadedImage conditions) {

        String result = "操作成功";

        String sql = "INSERT INTO " + tableName + " (filename, uploader_id, upload_time, result) VALUES (?, ?, ?, ?)";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, conditions.getFilename());
            preparedStatement.setString(2, conditions.getUploaderId());
            preparedStatement.setString(3, conditions.getUploadTime());
            preparedStatement.setString(4, conditions.getResult());


            preparedStatement.executeUpdate();
            preparedStatement.close();

        } catch (SQLException e) {
            logger.severe("Error executing query: " + e.getMessage());
            result = e.getMessage();
        }
        return result; //返回的是插入是否成功的提示信息
    }


}
