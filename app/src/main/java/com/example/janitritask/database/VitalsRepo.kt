package com.example.janitritask.database

class VitalsRepo(private val vitalsDAO: VitalsDAO) {
    suspend fun getAllVitals(): List<Vitals> = vitalsDAO.getAllVitals()
    suspend fun insertVitals(vitals: Vitals) = vitalsDAO.insertVitals(vitals)
}