package com.example.apicatdog

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.apicatdog.ui.screen.NavGraph
import com.example.apicatdog.ui.theme.APiCatDogTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            APiCatDogTheme {
                NavGraph()
            }
        }
    }


    @Preview(showBackground = true)
    @Composable
    fun GreetingPreview() {
        APiCatDogTheme {
            NavGraph()
        }
    }
}