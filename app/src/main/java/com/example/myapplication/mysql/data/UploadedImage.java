package com.example.myapplication.mysql.data;

public class UploadedImage {
    private String filename;
    private String uploader_id;
    private String upload_time;
    private String result;

    public UploadedImage(){this("","","","");}
    public UploadedImage(String filename,String uploader_id,String upload_time,String result){
        this.filename = filename;
        this.uploader_id = uploader_id;
        this.upload_time = upload_time;
        this.result = result;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getUploaderId() {
        return uploader_id;
    }

    public void setUploaderId(String uploader_id) {
        this.uploader_id = uploader_id;
    }

    public String getUploadTime() {
        return upload_time;
    }

    public void setUploadTime(String upload_time) {
        this.upload_time = upload_time;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
