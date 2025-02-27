package com.example.janitritask.entity

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.janitritask.database.Vitals
import com.example.janitritask.database.VitalsDAO

@Database(entities = [Vitals::class], version = 2)
abstract class VitalsDatabase() : RoomDatabase() {
    abstract fun VitalsDAO(): VitalsDAO
}