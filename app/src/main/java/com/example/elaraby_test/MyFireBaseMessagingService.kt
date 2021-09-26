package com.example.elaraby_test

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import android.util.Log
import android.widget.RemoteViews
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat.getSystemService
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

const val channelId = "notification_channel"
const val channelName = "com.example.elaraby_test"


class MyFireBaseMessagingService : FirebaseMessagingService() {


    override fun onNewToken(p0: String) {
        super.onNewToken(p0)
    }
    override fun onMessageReceived(remoteMessage: RemoteMessage) {
      if(remoteMessage.notification!=null){
          sendNotification(remoteMessage.notification!!.title!!,remoteMessage.notification!!.body!!)
      }
    }

     fun getRemoteView(title:String, message:String):RemoteViews{

        val remoteView=RemoteViews("com.example.elaraby_test",R.layout.notificationlayout)
        remoteView.setTextViewText(R.id.title,title)
        remoteView.setTextViewText(R.id.message,message)
        remoteView.setImageViewResource(R.id.icon,R.drawable.appicon)
        return remoteView
    }

    private fun sendNotification(title:String , message:String) {
        val intent = Intent(this, MainActivity2::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)

        val pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent, PendingIntent.FLAG_ONE_SHOT)

        var builder:NotificationCompat.Builder=NotificationCompat.Builder(applicationContext, channelId)
        builder.setSmallIcon(R.drawable.appicon)
            .setAutoCancel(true)
            .setVibrate(longArrayOf(1000,1000,1000,1000))
            .setOnlyAlertOnce(true)
        .setContentIntent(pendingIntent)

        builder=builder.setContent(getRemoteView(title,message))

        val notificationManager=getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            val notificationChannel=NotificationChannel(channelId, channelName,NotificationManager.IMPORTANCE_HIGH)
            notificationManager.createNotificationChannel(notificationChannel)
        }
        notificationManager.notify(0 /* ID of notification */, builder.build())
    }
}