package com.jan.products.fcm

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.jan.products.ui.main.MainActivity
import com.jan.products.R

class MessagingService : FirebaseMessagingService() {

    private var icon = 0
    private var data: Map<String, String>? = null


    override fun onMessageReceived(remoteMessage: RemoteMessage) {

        icon = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            R.mipmap.ic_launcher
        } else {
            R.mipmap.ic_launcher
        }
        data = remoteMessage.data
        val notification = remoteMessage.notification!!.body
        println("Notification: $notification")
        //val msg = data!!["message"]
        openApp(notification)
    }

    private fun openApp(msg: String?) {
        val contentIntent: PendingIntent
        val notificationIntent = Intent(Intent.ACTION_MAIN)
        notificationIntent.setClass(this, MainActivity::class.java)
        notificationIntent.flags =
            Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK
        notificationIntent.addCategory(Intent.CATEGORY_LAUNCHER)
        val mNotificationManager =
            this.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        contentIntent = PendingIntent.getActivity(
            this,
            0,
            notificationIntent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )
        createChannel(mNotificationManager)
        val mBuilder: NotificationCompat.Builder =
            NotificationCompat.Builder(this, getString(R.string.notification_id))
                .setSmallIcon(icon)
                .setContentTitle(resources.getString(R.string.app_name))
                .setTicker(msg)
                .setStyle(NotificationCompat.BigTextStyle().bigText(msg))
                .setContentText(msg)
        mBuilder.setVibrate(longArrayOf(1000, 1000, 1000))
        mBuilder.setLights(Color.BLUE, 3000, 3000)
        mBuilder.setContentIntent(contentIntent)
        mBuilder.setAutoCancel(true)
        mBuilder.priority = NotificationCompat.PRIORITY_DEFAULT
        mBuilder.color = ContextCompat.getColor(this, R.color.colorAccent)
        //mBuilder.setSound(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.sound));
        /*
        val intent = Intent("com.notification.intent")
        intent.putExtra("message", msg)
        val localBroadcastManager = LocalBroadcastManager.getInstance(this)
        localBroadcastManager.sendBroadcast(intent)

        */
        mNotificationManager.notify(NOTIFICATION_ID, mBuilder.build())
    }

    private fun createChannel(notificationManager: NotificationManager) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = getString(R.string.notification_id)
            val description = getString(R.string.notification_id)
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val mChannel = NotificationChannel(name, name, importance)
            //val mChannel = NotificationChannelGroup(name, name)

            mChannel.description = description
            mChannel.enableLights(true)
            mChannel.lightColor = Color.BLUE
            notificationManager.createNotificationChannel(mChannel)
        }
    }

    companion object {
        private const val NOTIFICATION_ID = 1
    }
}