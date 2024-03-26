package com.example.myapplication.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class HomeViewModel extends ViewModel {

    private final MutableLiveData<String> user_id;

    public HomeViewModel() {
        user_id = new MutableLiveData<>();
    }
    public LiveData<String> getUser_id() {
        return user_id;
    }

    public void setUser_id(String id) {
        user_id.setValue(id);
    }
}