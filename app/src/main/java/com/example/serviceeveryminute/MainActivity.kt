package com.example.serviceeveryminute

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.serviceeveryminute.ui.theme.ServiceEveryMinuteTheme
import kotlinx.coroutines.delay
import java.util.*
import kotlin.random.Random

class MainActivity : ComponentActivity() {


    //    private val viewModel by viewModels<MainViewModel>()
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)




        setContent {
            ServiceEveryMinuteTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    MainContent(viewModel)
                }
            }
        }
    }
}

@Composable
fun MainContent(viewModel: MainViewModel) {

    val context = LocalContext.current

   val backgroundColor by viewModel.color.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(backgroundColor)),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Button(onClick = {
            val intent = Intent(context, MyService::class.java)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                context.startForegroundService(intent)
            } else {
                context.startService(intent)
            }


        }) {
            Text(text = "Start Service")
        }


        Spacer(modifier = Modifier.height(10.dp))

        Button(onClick = {

            val intent = Intent(context, MyService::class.java)
            context.stopService(intent)

        }) {
            Text(text = "Stop Service")
        }


        Spacer(modifier = Modifier.height(10.dp))
        ServiceText(viewModel)



        Spacer(modifier = Modifier.height(30.dp))



        Button(onClick = {
            viewModel.changeBackgroundColor()
        }) {
            Text(text = "change background color")
        }

    }
}




// In your Composable function
@Composable
fun ServiceText(myViewModel: MainViewModel) {
//    val value by myViewModel.value.collectAsState()
//    Text(text = "The current value is $value")


   val context = LocalContext.current

    val value = remember { mutableStateOf(0) }

    val broadcastReceiver = remember {
        object : BroadcastReceiver() {
            override fun onReceive(context: Context, intent: Intent) {
                if (intent.action == "com.example.MY_ACTION") {
                    val newValue = intent.getIntExtra("value", 0)
                    value.value = newValue
                }
            }
        }
    }

//    LaunchedEffect(Unit) {
    DisposableEffect(Unit) {
        val intentFilter = IntentFilter().apply {
            addAction("com.example.MY_ACTION")
        }
        context.registerReceiver(broadcastReceiver, intentFilter)

        onDispose {
            context.unregisterReceiver(broadcastReceiver)
        }
    }

    Text(text = "The current value is ${value.value}")

}

