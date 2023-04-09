package com.example.rafdnevnjak.viewmodels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.rafdnevnjak.model.MyDate;

public class MyDateSelectedViewModel extends ViewModel {

    private final MutableLiveData<MyDate> date = new MutableLiveData<>();

    public MyDateSelectedViewModel(){

    }

    public MutableLiveData<MyDate> getDate() {
        return date;
    }
}
