package hr.tvz.android.listapp

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class FCMService : FirebaseMessagingService() {
    val TAG = "FCMService"

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)
        Log.d(TAG,"From: ${remoteMessage.from}")

        Toast.makeText(this,remoteMessage.data.toString(), Toast.LENGTH_LONG).show()

        if (remoteMessage.data.isNotEmpty()) {
            Log.d(TAG, "Message data payload: ${remoteMessage.data}")

            if (true) {
                scheduleJob()
            }else {
                handleNow()
            }
        }

        remoteMessage.notification?.let {
            Log.d(TAG, "Message notification body: ${it.body}")

            sendNotification(it.body.toString())
        }

    }

    private fun sendNotification(messageBody: String) {
         val intent = Intent(this, MainActivity::class.java)
         intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)

         val pendingIntent = PendingIntent.getActivity(this, 0,
             intent,
             PendingIntent.FLAG_ONE_SHOT)

         val channelId = getString(R.string.default_notification_channel_id)
         val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
         val notificationBuilder = NotificationCompat.Builder(
             this, channelId
         )
             .setContentTitle(getString(R.string.fcm_message))
             .setContentText(messageBody)
             .setAutoCancel(false)
             .setSound(defaultSoundUri)
             .setContentIntent(pendingIntent)

         val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

         if (Build.VERSION.SDK_INT > Build.VERSION_CODES.O) {
             val channel = NotificationChannel(channelId,
                 "Channel human readable title",
                 NotificationManager.IMPORTANCE_DEFAULT)
             notificationManager.createNotificationChannel(channel)
         }

         notificationManager.notify(0, notificationBuilder.build())

     }

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Log.d(TAG, token)
    }

    private fun handleNow() {
        Log.d(TAG, "Short lived task done")
    }

    private fun scheduleJob() {
        TODO("Not yet implemented")
    }
}