package com.example.rafdnevnjak.viewmodels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class CalendarViewModel extends ViewModel {

    private final MutableLiveData<String> mText = new MutableLiveData<>();

    public CalendarViewModel(){
        mText.setValue("Calendar viewModel");
    }

    public MutableLiveData<String> getmText() {
        return mText;
    }
}
