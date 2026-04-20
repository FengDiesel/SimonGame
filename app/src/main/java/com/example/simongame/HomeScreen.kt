package com.example.simongame

import android.content.res.Configuration
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import kotlin.text.ifEmpty

@Composable
fun HomeScreen(onEndGame: (String) -> Unit){
    val configuration = LocalConfiguration.current.orientation

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
    ){
        Text(text = stringResource(R.string.app_name), modifier = Modifier.align(Alignment.CenterHorizontally))


        if(configuration == Configuration.ORIENTATION_LANDSCAPE){
            Row(
                modifier = Modifier.fillMaxSize()
            ) {
                colorGrid(onColorClick = { color -> addColor(color) }, modifier = Modifier.weight(1f))

                Spacer(modifier = Modifier.width(10.dp))

                gameBody(sequence, onEndGame = { onEndGame(sequence); sequence = "" }, onClear = { sequence = ""}, modifier = Modifier.weight(1f))
            }
        }else{
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                colorGrid(onColorClick = { color -> addColor(color) }, modifier = Modifier.weight(1f))

                Spacer(modifier = Modifier.height(10.dp))

                gameBody(sequence, onEndGame = { onEndGame(sequence); sequence = "" }, onClear = { sequence = "" }, modifier = Modifier.weight(1f))
            }
        }
    }
}

@Composable
fun colorGrid(onColorClick: (String) -> Unit, modifier: Modifier = Modifier){
    val sizeModifier = Modifier.size(100.dp)
    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = modifier) {

        Row() {
            Box(modifier = sizeModifier.background(Color.Red).clickable{onColorClick("R")}) {}

            Box(modifier = sizeModifier.background(Color.Blue).clickable{onColorClick("B")}) {}
        }

        Row() {
            Box(modifier = sizeModifier.background(Color.Cyan).clickable{onColorClick("C")}) {}

            Box(modifier = sizeModifier.background(Color.Yellow).clickable{onColorClick("Y")}) {}
        }

        Row() {
            Box(modifier = sizeModifier.background(Color.Magenta).clickable{onColorClick("M")}) {}

            Box(modifier = sizeModifier.background(Color.Green).clickable{onColorClick("G")}) {}
        }
    }
}

@Composable
fun gameBody(sequence: String, onEndGame: (String) -> Unit, onClear: () -> Unit, modifier: Modifier = Modifier){
    Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally){
        Text(
            text = sequence.ifEmpty { stringResource(R.string.start_sequence) },
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
                .heightIn(min = 100.dp)
                .border(1.dp, Color.White, RoundedCornerShape(4.dp))
                .padding(8.dp),
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(8.dp))

        Row() {
            Button(onClick = { onClear() }) {
                Text(text = stringResource(R.string.delete))
            }

            Spacer(modifier = Modifier.width(10.dp))

            Button(onClick = { onEndGame(sequence) }) {
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