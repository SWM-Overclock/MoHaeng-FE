package com.example.moHaeng.location

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class LocationViewModel : ViewModel() {

    private val _locationList = MutableLiveData<List<SetLocationFragment.LocationListResponseDto>>()
    val locationList: LiveData<List<SetLocationFragment.LocationListResponseDto>> get() = _locationList
//    fun setPrimaryLocation(locationId: Long) {
//        val list = _locationList.value?.toMutableList()
//        val index = list?.indexOfFirst { it.id == locationId }
//        val item = list?.removeAt(index!!)
//        list.add(0, item!!)
//        _locationList.value = list
//    }

    fun setLocationList(list: List<SetLocationFragment.LocationListResponseDto>) {
        _locationList.value = list
    }
}