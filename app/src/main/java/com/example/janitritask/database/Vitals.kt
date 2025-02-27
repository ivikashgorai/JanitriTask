package com.example.janitritask.database;

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "vitals")
data class Vitals(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    var bloodPressure: String,
    var heartRate: String,
    var weight: Double,
    var kicks: Int,
    var vitalSavedDate: String
)