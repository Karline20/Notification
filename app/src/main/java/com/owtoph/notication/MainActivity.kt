package com.owtoph.notication

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.NotificationCompat
import androidx.core.app.RemoteInput
import com.owtoph.notication.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var activityMainBinding: ActivityMainBinding
    private val channelID = "com.owtoph.notication.channel1"
    private var notificationManager: NotificationManager? = null
    private val KEY_REPLY = "key_reply"

    @SuppressLint("ServiceCast")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        val view = activityMainBinding.root
        setContentView(view)
        notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        createNotificationChannel(channelID, "Legaspi Notification", "This is a sample notification")
        activityMainBinding.notify.setOnClickListener {
            displayNotification()
        }
    }

    fun displayNotification(){
        val notificationId = 45
        val tapResultIntent = Intent(this, SecondActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent = PendingIntent.getActivity(
            this,
            0,
            tapResultIntent,
            PendingIntent.FLAG_MUTABLE
        )

        //reply action 1
        val remoteInput: RemoteInput = RemoteInput.Builder(KEY_REPLY).run {
            setLabel("Insert your reply here")
            build()
        }

        val replyAction: NotificationCompat.Action = NotificationCompat.Action.Builder(
            0,
            "REPLY",
            pendingIntent
        ).addRemoteInput(remoteInput)
            .build()

        //action button 1
        val detailsIntent = Intent(this, DetailsActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent2 = PendingIntent.getActivity(
            this,
            0,
            detailsIntent,
            PendingIntent.FLAG_IMMUTABLE
        )
        val action2: NotificationCompat.Action = NotificationCompat.Action.Builder(0, "Details", pendingIntent2).build()

        //action button 2
        val settingsIntent = Intent(this, SettingsActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent3 = PendingIntent.getActivity(
            this,
            0,
            settingsIntent,
            PendingIntent.FLAG_IMMUTABLE
        )
        val action3: NotificationCompat.Action = NotificationCompat.Action.Builder(0, "Settings", pendingIntent3).build()

        val notification = NotificationCompat.Builder(this@MainActivity, channelID)
            .setContentTitle("Sample Title")
            .setContentText("This is a sample notification")
            .setSmallIcon(android.R.drawable.ic_dialog_alert) //Icon
            .setAutoCancel(true)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .addAction(action2)
            .addAction(action3)
            .addAction(replyAction)
            .build()
        notificationManager?.notify(notificationId,notification)
    }

    fun createNotificationChannel(id: String, name: String, channelDescription: String){
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            val importance =NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel(id, name, importance).apply {
                description = channelDescription
            }
            notificationManager?.createNotificationChannel(channel)
        }
    }
}