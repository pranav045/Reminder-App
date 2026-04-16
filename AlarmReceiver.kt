package com.example.sem6

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.widget.Toast
import androidx.compose.ui.platform.LocalContext

class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent?) {
        val alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM)
            ?: RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val ringtone = RingtoneManager.getRingtone(context, alarmUri)
        ringtone.play()
        Toast.makeText(context, "Reminder!", Toast.LENGTH_LONG).show()
    }

}
