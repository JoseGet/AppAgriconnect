package com.example.careiroapp.service

import android.app.NotificationManager
import androidx.core.app.NotificationCompat
import com.example.careiroapp.R
import com.example.careiroapp.consts.EventCodes
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import kotlin.random.Random

class MyFirebaseMessagingService(): FirebaseMessagingService() {

    private val notificationManager by lazy {
        getSystemService(NotificationManager::class.java)
    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)
    }

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)

        val messageData = message.data

        when {
            messageData["code"] == EventCodes.PAGAMENTO_PIX_CONFIRMADO -> {
                val notification = NotificationCompat.Builder(this, "agriconnect_notification")
                    .setSmallIcon(R.drawable.ic_agriconnect)
                    .setContentTitle("Pagamento Recebido!")
                    .setContentText("Oba! Seu pagamento Pix foi recebido e o pedido foi confirmado.")
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                    .setAutoCancel(true)
                    .build()

                notificationManager.notify(Random.nextInt(), notification)
            }
        }
    }
}