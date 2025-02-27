package com.example.janitritask.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.janitritask.database.Vitals
import com.example.janitritask.database.VitalsRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class VitalsViewModel(private val vitalsRepo: VitalsRepo) : ViewModel() {

    val vitalsList = MutableStateFlow<List<Vitals>>(emptyList())

    init {
        getAllVitals()
    }

    fun getAllVitals() {
        viewModelScope.launch(Dispatchers.IO) {
            vitalsList.value = vitalsRepo.getAllVitals()
        }
    }

    fun insertVitals(vitals: Vitals) {
        viewModelScope.launch(Dispatchers.IO) {
            vitalsRepo.insertVitals(vitals)
            getAllVitals()
        }
    }
}