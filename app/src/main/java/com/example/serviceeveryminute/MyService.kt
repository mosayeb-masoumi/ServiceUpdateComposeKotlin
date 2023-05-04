package com.example.serviceeveryminute

import android.app.*
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.app.NotificationCompat
import androidx.lifecycle.ViewModelProvider
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.*


class MyService: Service() {


//    private lateinit var viewModel: MainViewModel

//    private val coroutineScope = CoroutineScope(Dispatchers.Main)

    private val job = Job()
    private val scope = CoroutineScope(Dispatchers.IO + job)

    var counter = 0


//    private lateinit var timer: Timer
//    private val interval = 5000L // 5 seconds


    override fun onCreate() {
        super.onCreate()


//        viewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(application)
//            .create(MainViewModel::class.java)




//
//        timer = Timer()
//        timer.scheduleAtFixedRate(object : TimerTask() {
//            override fun run() {
//                val value = counter++
//                viewModel.updateDataState(value)
//            }
//        }, interval, interval)




        if (Build.VERSION.SDK_INT >= 26) {
            val CHANNEL_ID = "my_channel_01"
            val channel = NotificationChannel(
                CHANNEL_ID,
                "Channel human readable title",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            (getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager).createNotificationChannel(
                channel
            )
            val notification = NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("")
                .setContentText("").build()
            startForeground(1, notification)
        }
    }



    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {


        scope.launch {
            while (isActive){
                delay(5000)
                counter++
                performTask()
            }
        }

        return START_STICKY
    }

    private fun performTask() {

        Handler(Looper.getMainLooper()).post {
            Toast.makeText(this@MyService, "background is active $counter", Toast.LENGTH_SHORT).show()


        }
//        viewModel.updateDataState(counter)


        val intent = Intent().apply {
            action = "com.example.MY_ACTION"
            putExtra("value", counter)
        }
        sendBroadcast(intent)

    }

    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
        stopSelf()

    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

}