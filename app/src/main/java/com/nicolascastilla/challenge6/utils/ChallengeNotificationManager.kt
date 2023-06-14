package com.nicolascastilla.challenge6.utils

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.nicolascastilla.challenge6.MainActivity
import com.nicolascastilla.challenge6.R
import com.nicolascastilla.challenge6.activities.StartChatActivity
import javax.inject.Inject

class ChallengeNotificationManager@Inject constructor(
    private val context: Context,
) {

    fun setLocalNotification( phone:String, message:String, idChat:String){



        val resultIntent = Intent(context, StartChatActivity::class.java).apply {
            //putExtra("SONG_POSITION",0)
            //setAction("$id")todo enviar los datos que llegan
            flags= Intent.FLAG_ACTIVITY_SINGLE_TOP
        }
        var pendigFlag = PendingIntent.FLAG_UPDATE_CURRENT
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            pendigFlag = PendingIntent.FLAG_MUTABLE
        }
        val resultPendingIntent = PendingIntent.getActivity(context,0,resultIntent, pendigFlag)
        val notificationManager = context?.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val channelId = CHANNEL_ID
        val channel = NotificationChannel(
            channelId,
            "Some song",
            NotificationManager.IMPORTANCE_LOW
        )
        notificationManager.createNotificationChannel(channel)
        val builder = NotificationCompat.Builder(context, channelId)
            .setContentTitle("El numero del que manda")
            .setContentText("El mensaje va a qui si alcanso")
            .setSmallIcon(R.drawable.speech_bubble)
            .setStyle(NotificationCompat.DecoratedCustomViewStyle())
            .setPriority(NotificationCompat.PRIORITY_LOW)
            .setContentIntent(resultPendingIntent)
            .setAutoCancel(true)
            .setOngoing(false)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            with(NotificationManagerCompat.from(context)){
                if (ActivityCompat.checkSelfPermission(
                        context,
                        Manifest.permission.POST_NOTIFICATIONS
                    ) != PackageManager.PERMISSION_GRANTED
                ) {

                    return
                }
                notify(0,builder.build())
            }
        }else{
            notificationManager.notify(0, builder.build())
        }

    }


    companion object {
        const val CHANNEL_ID = "MusicServiceChannel"

    }
}