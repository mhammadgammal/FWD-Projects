package com.udacity

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.app.NotificationCompat

private const val NOTIFICATION_ID = 0
private const val REQUEST_CODE = 0

fun NotificationManager.sendNotification(context: Context, state: String, fileName: String){

    val toDetailActivityIntent = Intent(context, DetailActivity::class.java).apply {
        putExtra("download_state", state)
        putExtra("filename_extra", fileName)
    }
    val pendingIntent = PendingIntent.getActivity(
        context,
        NOTIFICATION_ID,
        toDetailActivityIntent,
        PendingIntent.FLAG_UPDATE_CURRENT
    )

    val builder = NotificationCompat.Builder(
        context,
        context.getString(R.string.notification_channel_id)
    )
        .setSmallIcon(R.drawable.ic_assistant_black_24dp)
        .setContentTitle(R.string.notification_title.toString())
        .setContentText(R.string.notification_description.toString())
        .setContentIntent(pendingIntent)
        .setAutoCancel(true)
        .addAction(
            R.drawable.abc_vector_test,
            context.getString(R.string.notification_button),
            pendingIntent
        )
        .setPriority(NotificationCompat.PRIORITY_DEFAULT)

    notify(NOTIFICATION_ID, builder.build())

}

fun NotificationManager.cancelNotification(){
    cancelAll()
}
