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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.simongame.GameDetailScreen
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
 * Contiene NavHost per la navigazione tra le schermate e inizializza i ViewModel per la gestione dei dati.
 */
@Composable
fun SimonGame() {
    val navController = rememberNavController()
    val context = LocalContext.current

    // Factory personalizzata per aggiungere il DAO nel GameListViewModel
    val gameListVM: GameListViewModel = viewModel(
        factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                val dao = SimonDatabase.getDatabase(context).gameResultDao()
                return GameListViewModel(dao) as T
            }
        }
    )

    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = "statsscreen",
            modifier = Modifier.padding(innerPadding)
        ) {
            composable("statsscreen") {
                StatsScreen(
                    gameListVM.history,
                    onGameScreen = {
                        navController.navigate("gamescreen")
                    },
                    onGameDetail = { gameID ->
                        navController.navigate("gamedetailscreen/$gameID")
                    }
                )
            }

            composable("gamescreen") {
                // GameViewModel istanziato dentro gamescreen per limitare il ciclo di vita alla sola schermata di gioco
                val gameVM: GameViewModel = viewModel()

                GameScreen(
                    gameVM,
                    onEndGame = {
                        gameListVM.addGame(it)
                        navController.navigate("statsscreen") {
                            popUpTo("statsscreen") { inclusive = true }
                        }
                    },
                    onNullGame = { navController.popBackStack() }
                )
            }

            composable("gamedetailscreen/{gameID}") { backStackEntry ->
                val gameID = backStackEntry.arguments?.getString("gameID") ?: "NULL"
                GameDetailScreen(
                    gameID,
                    gameListVM.history,
                    onExit = {
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