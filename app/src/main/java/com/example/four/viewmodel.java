package com.example.four;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.android.gms.maps.model.LatLng;

public class viewmodel extends ViewModel {

    private final MutableLiveData<LatLng> shareddata=new MutableLiveData<>();

    public void setdata(LatLng data)
    {
        shareddata.setValue(data);
    }
    public MutableLiveData<LatLng> getShareddata()
    {
        return shareddata;
    }
}

