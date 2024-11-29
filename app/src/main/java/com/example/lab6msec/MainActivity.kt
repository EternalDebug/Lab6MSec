package com.example.lab6msec

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.lab6msec.data.HRep
import com.example.lab6msec.navigation.AppNavHost
import com.example.lab6msec.ui.theme.Lab6MSecTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val context = LocalContext.current
            val hrep = HRep(context)
            val navController: NavHostController = rememberNavController()
            Lab6MSecTheme {

                Surface(
                    modifier = Modifier.fillMaxSize(),
                ) {
                    AppNavHost(navController = navController, repka = hrep)
                }

            }
        }
    }
}
