package com.example.simongame

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun FinalScreen(history: List<String>, onBackPress: () -> Unit){

    BackHandler() {
        onBackPress()
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item{
            Text(
                text = stringResource(R.string.score),
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 16.dp)
            )
        }

        items(history) { sequence ->
            val letters = sequence.split(" - ")
            var expansion by rememberSaveable { mutableStateOf(false) }

            Row(modifier = Modifier.padding(vertical = 10.dp), verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = if(sequence.isEmpty()) "0" else letters.count().toString(),
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.width(20.dp))

                Text(
                    text = if(sequence.isEmpty()) stringResource(R.string.empty_play) else sequence,
                    maxLines = if(expansion) 10 else 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.weight(1f).clickable { expansion = !expansion }
                )
            }

            HorizontalDivider( thickness = 2.dp )
        }
    }
}

@Preview
@Composable
fun FinalScreenPreview(){
    FinalScreen(
        history = listOf(
            "R, G, B, Y",
            "",
            "R, G, B, M, Y, C, R, G, B, M, Y, C, R, G, C, C, Y, B, M, R, G, B, M, Y, C, R, G, B, M, Y, C, R, G, C, C, Y, B, M",
            "R, R, R, Y, Y, Y",
            "G, B, M, Y, C, R, M, Y, C, R, G, B, M, Y, C, R, M, Y, C, R, G, C, C, Y, B, M",
            ""
        ),
        onBackPress = {}
    )
}