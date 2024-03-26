package com.example.myapplication.mysql.data;

public class Employee {
    private String id;
    private String name;
    private String phone;
    private String userId;
    private String password;
    private String description;

    public Employee(){this("","","","","","");}
    public Employee(String id,String name,String phone,String userId,String password,String description){
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.userId = userId;
        this.password = password;
        this.description = description;
    }

    public String getId() {return id;}
    public void setId(String id) {this.id = id;}
    public String getName() {return name;}
    public void setName(String name) {this.name = name;}
    public String getPhone() {return phone;}
    public void setPhone(String phone) {this.phone = phone;}
    public String getUserId() {return userId;}
    public void setUserId(String userId) {this.userId = userId;}
    public String getPassword() {return password;}
    public void setPassword(String password) {this.password = password;}
    public String getDescription() {return description;}
    public void setDescription(String description) {this.description = description;}
}

