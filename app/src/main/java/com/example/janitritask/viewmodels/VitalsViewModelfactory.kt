package com.example.janitritask.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.janitritask.database.VitalsRepo


class VitalsViewModelFactory(private val vitalsRepo: VitalsRepo) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(VitalsViewModel::class.java)) {
            return VitalsViewModel(vitalsRepo) as T
        }
        return super.create(modelClass)
    }
}