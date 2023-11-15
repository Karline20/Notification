package com.owtoph.notication

import android.app.NotificationManager
import android.app.RemoteInput
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.NotificationCompat
import com.owtoph.notication.databinding.ActivitySecondBinding

class SecondActivity : AppCompatActivity() {
    lateinit var activitySecondBinding: ActivitySecondBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activitySecondBinding = ActivitySecondBinding.inflate(layoutInflater)
        val view = activitySecondBinding.root
        setContentView(view)

        receiveInput()
    }

    private fun receiveInput() {
        val KEY_REPLY = "key_reply"
        val intent = this.intent
        val remoteInput = RemoteInput.getResultsFromIntent(intent)
        if (remoteInput!=null){
            val inputString = remoteInput.getCharSequence(KEY_REPLY)
            activitySecondBinding.input.text = inputString

            val channelID = "com.owtoph.notication.channel1"
            val notificationId = 45

            val repliedNotification = NotificationCompat.Builder(this, channelID)
                .setSmallIcon(android.R.drawable.ic_dialog_info)
                .setContentText("Your reply received")
                .build()
            val notificationManager: NotificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.notify(notificationId, repliedNotification)
        }
    }
}