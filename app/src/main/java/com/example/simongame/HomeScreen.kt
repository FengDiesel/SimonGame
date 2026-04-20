package com.example.simongame

import android.widget.TextView
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource

@Composable
fun HomeScreen(onEndGame: (String) -> Unit){
    var sequence by rememberSaveable { mutableStateOf("") }

    fun addColor(letter: String) {
        if (sequence.isEmpty()) {
            sequence = letter
        } else {
            sequence += " - $letter"
        }
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val sizeModifier = Modifier.size(100.dp)

        Text(text = stringResource(R.string.app_name), modifier = Modifier.align(Alignment.CenterHorizontally))

        Spacer(modifier = Modifier.height(8.dp).fillMaxWidth())


        Column() {

            Row() {
                Box(modifier = sizeModifier.background(Color.Red).clickable{addColor("R")}) {}

                Box(modifier = sizeModifier.background(Color.Blue).clickable{addColor("B")}) {}
            }
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Row() {
                Box(modifier = sizeModifier.background(Color.Cyan).clickable{addColor("C")}) {}

                Box(modifier = sizeModifier.background(Color.Yellow).clickable{addColor("Y")}) {}
            }
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Row() {
                Box(modifier = sizeModifier.background(Color.Magenta).clickable{addColor("M")}) {}

                Box(modifier = sizeModifier.background(Color.Green).clickable{addColor("G")}) {}
            }
        }

        Spacer(modifier = Modifier.height(8.dp).fillMaxWidth())

        Text(
            text = sequence.ifEmpty { stringResource(R.string.start_sequence) },
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(min = 100.dp)
                .border(1.dp, Color.White, RoundedCornerShape(4.dp))
                .padding(8.dp),
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(8.dp).fillMaxWidth())

        Row() {
            Button(onClick = { sequence = "" }) {
                Text(text = stringResource(R.string.delete))
            }

            Spacer(modifier = Modifier.width(10.dp))

            Button(onClick = {
                onEndGame(sequence)
                sequence = ""
            }) {
                Text(text = stringResource(R.string.end_game))
            }
        }
    }
}


@Preview
@Composable
fun HomeScreenPreview(){
    HomeScreen(
        onEndGame = TODO()
    )
}