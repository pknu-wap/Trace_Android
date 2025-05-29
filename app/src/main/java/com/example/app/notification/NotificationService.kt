package com.example.app.notification

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.example.domain.model.post.Emotion
import com.example.domain.repository.NotificationRepository
import com.example.main.MainActivity
import com.example.trace.R
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class NotificationService : FirebaseMessagingService() {

    @Inject
    @ApplicationContext
    lateinit var context: Context

    @Inject
    lateinit var notificationRepository: NotificationRepository

    private val scope = CoroutineScope(Dispatchers.IO + SupervisorJob())

    override fun onNewToken(token: String) {
        super.onNewToken(token)

        scope.launch {
            notificationRepository.updateDeviceToken(token)
        }
    }

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)

        val data = message.data

        val title = data["title"] ?: "흔적"
        val body = data["body"] ?: ""
        val type = data["type"]

        val intent = Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
        }

        if (data.isNotEmpty()) {
            data.forEach { (key, value) -> intent.putExtra(key, value) }
        }

        val pendingIntent = PendingIntent.getActivity(
            context,
            0,
            intent,
            PendingIntent.FLAG_CANCEL_CURRENT or PendingIntent.FLAG_IMMUTABLE,
        )

        val emotionEmoji = if (type == "emotion" && data["emotion"] != null) {
            val emotion = Emotion.fromString(data["emotion"])

            val emotionResId = when (emotion) {
                Emotion.HEARTWARMING -> com.example.designsystem.R.drawable.hearwarming
                Emotion.LIKEABLE -> com.example.designsystem.R.drawable.likeable
                Emotion.TOUCHING -> com.example.designsystem.R.drawable.touching
                Emotion.IMPRESSIVE -> com.example.designsystem.R.drawable.impressive
                Emotion.GRATEFUL -> com.example.designsystem.R.drawable.grateful
                else -> null
            }

            if (emotionResId == null) return

            BitmapFactory.decodeResource(
                context.resources,
                emotionResId
            )
        } else null

        val builder = NotificationCompat.Builder(context, BACKGROUND_CHANNEL_ID)
            .setSmallIcon(com.example.designsystem.R.drawable.app_icon_pencil)
            .setLargeIcon(emotionEmoji)
            .setColor(ContextCompat.getColor(context, R.color.white))
            .setContentTitle(title)
            .setContentText(body)
            .setShowWhen(true)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)


        val notificationManager: NotificationManager =
            context.getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(System.currentTimeMillis().toInt(), builder.build())
    }

    override fun onDestroy() {
        super.onDestroy()
        scope.cancel()
    }

    companion object {
        const val BACKGROUND_CHANNEL_ID = "background_channel_id"
        const val BACKGROUND_CHANNEL = "백그라운드 알림"
        const val BACKGROUND_CHANNEL_DESCRIPTION = "백그라운드 알림을 관리하는 채널입니다."
    }
}