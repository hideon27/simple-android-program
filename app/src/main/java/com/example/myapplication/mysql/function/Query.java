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

public class Query {
    private Connection connection;
    private Logger logger;

    public Query(Connection connection) {
        this.connection = connection;
        this.logger = Logger.getLogger(Query.class.getName());
    }

    private void appendCondition(StringBuilder sqlBuilder, String columnName, String value, boolean hasConditions) {
        if (!hasConditions) {
            sqlBuilder.append(" WHERE ");
        } else {
            sqlBuilder.append(" AND ");
        }
        sqlBuilder.append(columnName).append(" = '").append(value).append("'");
    }

    //employee表查询函数
    public List<Employee> query(String tableName, Employee conditions) {

        List<Employee> resultList = new ArrayList<>();  //创建带有MySQLQuery结构的列表代替数据集

        StringBuilder sqlBuilder = new StringBuilder("SELECT * FROM ").append(tableName);
        boolean hasConditions = false;

        if (conditions != null) {
            if (conditions.getId() != null) {
                appendCondition(sqlBuilder, "id", conditions.getId(), hasConditions);
                hasConditions = true;
            }
            if (conditions.getName() != null) {
                appendCondition(sqlBuilder, "name", conditions.getName(), hasConditions);
                hasConditions = true;
            }
            if (conditions.getPhone() != null) {
                appendCondition(sqlBuilder, "phone", conditions.getPhone(), hasConditions);
                hasConditions = true;
            }
            if (conditions.getUserId() != null) {
                appendCondition(sqlBuilder, "userid", conditions.getUserId(), hasConditions);
                hasConditions = true;
            }
            if (conditions.getPassword() != null) {
                appendCondition(sqlBuilder, "password", conditions.getPassword(), hasConditions);
                hasConditions = true;
            }
            if (conditions.getDescription() != null) {
                appendCondition(sqlBuilder, "description", conditions.getDescription(), hasConditions);
            }
        }

        ResultSet resultSet = null;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sqlBuilder.toString());
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Employee result = new Employee(resultSet.getString("id"),resultSet.getString("name"),resultSet.getString("phone")
                ,resultSet.getString("userid"),resultSet.getString("password"),resultSet.getString("description"));
                resultList.add(result);
            }
            resultSet.close();
            preparedStatement.close();
        } catch (SQLException e) {
            logger.severe("Error executing query: " + e.getMessage());
        }
        return resultList;
    }

    //UploadedImage表查询函数
    public List<UploadedImage> query(String tableName, UploadedImage conditions) {

        List<UploadedImage> resultList = new ArrayList<>();  //创建带有MySQLQuery结构的列表代替数据集

        StringBuilder sqlBuilder = new StringBuilder("SELECT * FROM ").append(tableName);
        boolean hasConditions = false;

        if (conditions != null) {
            if (conditions.getFilename() != null) {
                appendCondition(sqlBuilder, "filename", conditions.getFilename(), hasConditions);
                hasConditions = true;
            }
            if (conditions.getUploaderId() != null) {
                appendCondition(sqlBuilder, "uploader_id", conditions.getUploaderId(), hasConditions);
                hasConditions = true;
            }
            if (conditions.getUploadTime() != null) {
                appendCondition(sqlBuilder, "upload_time", conditions.getUploadTime(), hasConditions);
                hasConditions = true;
            }
            if (conditions.getResult() != null) {
                appendCondition(sqlBuilder, "result", conditions.getResult(), hasConditions);
                hasConditions = true;
            }
        }
        sqlBuilder.append(" ORDER BY upload_time DESC"); //时间降序排序

        ResultSet resultSet = null;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sqlBuilder.toString());
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                UploadedImage result = new UploadedImage(resultSet.getString("filename"),resultSet.getString("uploader_id"),
                        resultSet.getString("upload_time"),resultSet.getString("result"));
                resultList.add(result);
            }
            resultSet.close();
            preparedStatement.close();
        } catch (SQLException e) {
            logger.severe("Error executing query: " + e.getMessage());
        }
        return resultList;
    }



}
