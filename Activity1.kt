package com.example.sem6


import android.Manifest
import java.util.Calendar
import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.widget.Button
import android.widget.TimePicker
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresPermission
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.unit.dp
import com.example.sem6.ui.theme.Sem6Theme
import java.util.*
import kotlin.jvm.java

class Activity1 : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            // Provide a default name here
            ActivityScreen()
        }
    }
}

@Preview(showBackground=true, widthDp = 500, heightDp=500)
@Composable
fun ActivityScreen(){
    val context=LocalContext.current
    var hour by remember{mutableStateOf( Calendar.getInstance().get(Calendar.HOUR_OF_DAY))}
    var minute by remember { mutableStateOf(Calendar.getInstance().get(Calendar.MINUTE)) }

    var assignment by remember { mutableStateOf("") }
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier=Modifier.fillMaxSize()
    ) {
        Text("Alarm Clock")
        Spacer(Modifier.height(22.dp))
        AndroidView(
            factory={ctx->
                TimePicker(ctx).apply {
                    setIs24HourView(true)
                    setOnTimeChangedListener { _,h, m ->
                        hour = h
                        minute = m
                    }
                }
            }
        )
        Spacer(Modifier.height(12.dp))
        TextField(value = assignment,
            onValueChange = {assignment=it})
        Spacer(Modifier.height(12.dp))
        Button(onClick = {
            val calendar=Calendar.getInstance().apply {
                set(Calendar.HOUR_OF_DAY,hour)
                set(Calendar.MINUTE,minute)
                set(Calendar.SECOND,0)
                if(before(Calendar.getInstance())){
                    add(Calendar.DATE,1)
                }
            }
            if(checkExactAlarmPermission1(context)){
                SetAlarm1(context,calendar.timeInMillis)
            }
            else{
                requireExactAlarmPermission1(context)
            }
            Toast.makeText(context,"Reminder: $assignment",Toast.LENGTH_SHORT).show()
        }) {Text("Set Alarm") }
    }

}
fun checkExactAlarmPermission1(context:Context):Boolean{
    return if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.S){
        val alarmManager= context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager.canScheduleExactAlarms()
    } else true;
}
fun requireExactAlarmPermission1(context:Context){
    if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.S){
        try{
            val intent= Intent(Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM)
            context.startActivity(intent)
        } catch(e:Exception){
            Toast.makeText(context,"Error occured!",Toast.LENGTH_SHORT).show()
        }
    }
}
@RequiresPermission(Manifest.permission.SCHEDULE_EXACT_ALARM)
@SuppressLint("Alarm Clock")
fun SetAlarm1(context: Context,triggerMillis:Long){
    val alarmManager=context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
    val intent=Intent(context, AlarmReceiver::class.java)
    val pendingIntent= PendingIntent.getBroadcast(
        context,
        triggerMillis.hashCode(),
        intent,
        PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
    )
    alarmManager.setExactAndAllowWhileIdle(
        AlarmManager.RTC_WAKEUP,
        triggerMillis,
        pendingIntent
    )
    Toast.makeText(context,"Alarm set at ${Date(triggerMillis)} successfully",Toast.LENGTH_SHORT).show()
}


