package com.example.simongame

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun HomeScreen(){
    Column(modifier = Modifier.fillMaxSize()) {
        Text(text = "Simon Game", modifier = Modifier.align(Alignment.CenterHorizontally))

        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Row() {
                Box(modifier = Modifier.background(Color.Red)) {}

                Box(modifier = Modifier.background(Color.Blue)) {}

                Box(modifier = Modifier.background(Color.Green)) {}
            }
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Row() {
                Box(modifier = Modifier.background(Color.Cyan)) {}

                Box(modifier = Modifier.background(Color.Yellow)) {}

                Box(modifier = Modifier.background(Color.Magenta)) {}
            }
        }
    }
}


@Preview
@Composable
fun HomeScreenPreview(){
    HomeScreen()
}