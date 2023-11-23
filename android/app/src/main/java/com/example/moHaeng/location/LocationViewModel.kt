package com.example.moHaeng.location

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class LocationViewModel : ViewModel() {

    private val _locationList = MutableLiveData<List<SetLocationFragment.LocationListResponseDto>>()
    val locationList: LiveData<List<SetLocationFragment.LocationListResponseDto>> get() = _locationList

    fun setLocationList(list: List<SetLocationFragment.LocationListResponseDto>) {
        _locationList.value = list
    }
}