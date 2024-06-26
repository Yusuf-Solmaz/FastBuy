package com.yusuf.yusuf_mucahit_solmaz_final.service


import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.yusuf.yusuf_mucahit_solmaz_final.MainActivity
import com.yusuf.yusuf_mucahit_solmaz_final.R


const val channelId= "notification_channel"
const val channelName = "com.yusuf.yusuf_mucahit_solmaz_final.service"
private const val NOTIFICATION_ID = 1

class NotificationFirebaseMessagingService: FirebaseMessagingService() {

    override fun onNewToken(token: String) {
        Log.d("Token", token)
        super.onNewToken(token)
    }

    override fun onMessageReceived(message: RemoteMessage) {
        message.notification?.let {
            Log.d("Message body", "${it.body}")
            generateNotification(it.title ?: "Title", it.body ?: "Body")
        }
    }

    private fun generateNotification(title: String, description: String){
        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)

        val pendingIntent = PendingIntent.getActivity(this,0,intent,
            PendingIntent.FLAG_ONE_SHOT or PendingIntent.FLAG_IMMUTABLE)

        showNotification(this, title, description, pendingIntent)

    }

    private fun showNotification(context: Context, title: String, content: String,intent: PendingIntent) {
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                "Daily Recommendations",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            notificationManager.createNotificationChannel(channel)
        }

        val notification = NotificationCompat.Builder(context, channelId)
            .setContentTitle(title)
            .setContentText(content)
            .setSmallIcon(R.drawable.app_icon)
            .setContentIntent(intent)
            .build()

        notificationManager.notify(NOTIFICATION_ID, notification)
    }

}