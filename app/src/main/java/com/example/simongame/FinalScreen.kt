package com.example.simongame

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun FinalScreen(history: List<String>, onBackPress: () -> Unit){
    BackHandler() {
        onBackPress()
    }

    LazyColumn(
        modifier = Modifier.fillMaxSize().padding(8.dp)
    ) {
        items(history) { sequence ->
            val letters = sequence.split(" - ")
            Row() {
                Text(text = "${if(letters.isEmpty()) 0 else letters.count()}")

                Spacer(modifier = Modifier.width(10.dp))

                Text(text = sequence, maxLines = 1, overflow = TextOverflow.Ellipsis, modifier = Modifier.weight(1f))
            }
        }
    }
}

@Preview
@Composable
fun FinalScreenPreview(){
    FinalScreen(
        history = TODO(),
        onBackPress = TODO()
    )
}