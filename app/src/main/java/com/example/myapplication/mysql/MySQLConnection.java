package com.example.myapplication.mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQLConnection {

    private static final String DEFAULT_SERVER_IP = "your server ip";
    private static final int DEFAULT_PORT = 3306;
    private static final String DEFAULT_DATABASE_NAME = "database name";
    private static final String DEFAULT_USERNAME = "username";
    private static final String DEFAULT_PASSWORD = "password";
    private Connection connection = null;
    private String errorMessage = null; // 新增错误消息字段

    public MySQLConnection() {
        this(DEFAULT_SERVER_IP, DEFAULT_PORT, DEFAULT_DATABASE_NAME, DEFAULT_USERNAME, DEFAULT_PASSWORD);
    }

    public MySQLConnection(String databaseName){
        this(DEFAULT_SERVER_IP, DEFAULT_PORT, databaseName, DEFAULT_USERNAME, DEFAULT_PASSWORD);
    }

    public MySQLConnection(String serverIP, int port, String databaseName, String username, String password) {

        //String url = "jdbc:mysql://" + serverIP + ":" + port + "/" + databaseName + "?serverTimezone=UTC&useSSL=false&allowPublicKeyRetrieval=true&useUnicode=true&autoReconnect=true&zeroDateTimeBehavior=convertToNull&allowMultiQueries=true";
        String url = "jdbc:mysql://" + serverIP + ":" + port + "/" + databaseName;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            //Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(url, username, password);
            System.out.println("Connected to the MySQL server successfully.");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            errorMessage = e.getMessage(); // 记录错误消息
        }
    }

    public Connection getConnection() {
        return connection;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
