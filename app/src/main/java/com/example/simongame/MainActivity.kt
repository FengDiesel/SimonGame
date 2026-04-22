package com.example.simongame

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.simongame.ui.theme.SimonGameTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SimonGameTheme {
                SimonGame()
            }
        }
    }
}


/**
 * Componente principale di SimonGame.
 * Contiene NavHost per la navigazione tra le schermate e memorizza lo storico delle partite garantendo
 * la sopravvivenza durante i cambi di rotazione.
 */
@Composable
fun SimonGame() {
    val navController = rememberNavController()
    var history by rememberSaveable { mutableStateOf(emptyList<String>()) }

    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = "homescreen",
            modifier = Modifier.padding(innerPadding)
        ) {
            composable("homescreen") {
                HomeScreen(
                    onEndGame = { newSeq ->
                        history = history + newSeq
                        navController.navigate("finalscreen")
                    }
                )
            }

            composable("finalscreen") {
                FinalScreen(
                    history = history,
                    onBackPress = {
                        navController.popBackStack()
                    }
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    SimonGame()
}