package com.example.simongame

import android.widget.TextView
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
fun HomeScreen(){
    var initialText = stringResource(R.string.start_sequence)
    var sequence by rememberSaveable { mutableStateOf(initialText)}

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val sizeModifier = Modifier.size(100.dp)

        Text(text = stringResource(R.string.app_name), modifier = Modifier.align(Alignment.CenterHorizontally))

        Spacer(modifier = Modifier.height(8.dp).fillMaxWidth())


        Column() {

            Row() {
                Box(modifier = sizeModifier.background(Color.Red)) {}

                Box(modifier = sizeModifier.background(Color.Blue)) {}
            }
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Row() {
                Box(modifier = sizeModifier.background(Color.Cyan)) {}

                Box(modifier = sizeModifier.background(Color.Yellow)) {}
            }
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Row() {
                Box(modifier = sizeModifier.background(Color.Magenta)) {}

                Box(modifier = sizeModifier.background(Color.Green)) {}
            }
        }

        Spacer(modifier = Modifier.height(8.dp).fillMaxWidth())

        Text(
            text = sequence,
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(min = 100.dp)
                .border(1.dp, Color.White, RoundedCornerShape(4.dp))
                .padding(8.dp),
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(8.dp).fillMaxWidth())

        Row() {
            Button(onClick = {sequence = initialText}){
                Text(text = stringResource(R.string.delete))
            }

            Spacer(modifier = Modifier.width(10.dp))

            Button(onClick = {}){
                Text(text = stringResource(R.string.end_game))
            }
        }
    }
}


@Preview
@Composable
fun HomeScreenPreview(){
    HomeScreen()
}