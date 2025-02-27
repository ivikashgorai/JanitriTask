package com.example.janitritask.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.janitritask.MainActivity
import com.example.janitritask.R
import java.util.concurrent.TimeUnit

class VitalsWorker(context: Context, workerParams: WorkerParameters) :
    Worker(context, workerParams) {

    override fun doWork(): Result {
        showNotification()
        return Result.success()
    }

    private fun showNotification() {
        val context = applicationContext
        val intent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }

        val pendingIntent = PendingIntent.getActivity(
            context, 0, intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val channel = NotificationChannel(
            "vitals_channel",
            "Vitals Notifications",
            NotificationManager.IMPORTANCE_HIGH
        ).apply {
            description = "Reminds user to check vitals"
        }
        notificationManager.createNotificationChannel(channel)


        val notification = NotificationCompat.Builder(context, "vitals_channel")
            .setContentTitle("Time to log your vitals!")
            .setContentText("Stay on top of your health. Please update your vitals now!")
            .setSmallIcon(R.drawable.app_logo)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)
            .build()

        notificationManager.notify(1, notification)
    }
}

fun scheduleVitalsReminder(context: Context) {
    val workRequest = PeriodicWorkRequestBuilder<VitalsWorker>(5, TimeUnit.HOURS)
        .setInitialDelay(5, TimeUnit.HOURS)
        .build()

    WorkManager.getInstance(context).enqueueUniquePeriodicWork(
        "VitalsReminder",
        ExistingPeriodicWorkPolicy.UPDATE,
        workRequest
    )
}