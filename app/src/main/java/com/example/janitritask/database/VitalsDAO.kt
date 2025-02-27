package com.example.janitritask.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface VitalsDAO {

    @Query("Select * from vitals")
    suspend fun getAllVitals(): List<Vitals>

    @Insert
    suspend fun insertVitals(vitals: Vitals)
}