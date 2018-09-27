package com.kotlin.siddiqz.kotlin_android_helloworld.firebase

import android.content.Intent
import android.support.v4.content.LocalBroadcastManager
import android.util.Log

import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import java.util.HashMap

class MyFirebaseMessagingService : FirebaseMessagingService() {
    private var broadcaster: LocalBroadcastManager? = null

    override fun onCreate() {
        broadcaster = LocalBroadcastManager.getInstance(this)
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage?) {

        Log.d("****", "From: " + remoteMessage!!.from!!)
        var data: Map<String, String>? = null

        // Check if message contains a data payload.
        if (remoteMessage.data.size > 0) {

            Log.d("****", "Message data payload: " + remoteMessage.data)

            data = remoteMessage.data
            if (/* Check if data needs to be processed by long running job */ false) {
                // For long-running tasks (10 seconds or more) use Firebase Job Dispatcher.
            } else {
                // Handle message within 10 seconds
                val intent = Intent("MainActivity")
                intent.putExtra("data", HashMap(data))
                broadcaster!!.sendBroadcast(intent)
            }

        }

        // Check if message contains a notification payload.
        if (remoteMessage.notification != null) {
            Log.d("****", "Message Notification Body: " + remoteMessage.notification!!.body!!)
        }

        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated. See sendNotification method below.
    }
}
