package com.example.simongame

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun FinalScreen(history: List<String>, onBackPress: () -> Unit){
    Column() {
        for (sequence in history){
            Text(sequence)
        }
    }
}