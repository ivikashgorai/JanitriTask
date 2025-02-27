package com.example.janitritask

import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.room.Room
import com.example.janitritask.database.VitalsRepo
import com.example.janitritask.entity.VitalsDatabase
import com.example.janitritask.notification.scheduleVitalsReminder
import com.example.janitritask.viewmodels.VitalsViewModel
import com.example.janitritask.viewmodels.VitalsViewModelFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(
                    this,
                    android.Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(android.Manifest.permission.POST_NOTIFICATIONS),
                    1
                )
            }
        }

        scheduleVitalsReminder(this)

        setContent {
            val vitalsDatabase by lazy {
                Room.databaseBuilder(
                    this.applicationContext,
                    VitalsDatabase::class.java,
                    "vitals_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
            }
            val vitalsRepo by lazy {
                VitalsRepo(vitalsDatabase.VitalsDAO())
            }
            val vitalsViewModel: VitalsViewModel by viewModels {
                VitalsViewModelFactory(vitalsRepo)
            }
            HomeScreen(vitalsViewModel)
        }
    }
}