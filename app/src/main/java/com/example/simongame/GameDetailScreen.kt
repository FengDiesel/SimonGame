package com.example.simongame

import android.graphics.Paint
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlin.text.ifEmpty

@Composable
fun GameDetailScreen(gameID: String, history: List<GameResult>, onExit: ()-> Unit){
    var game = history.find { it.gameID == gameID }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Text(
            text = stringResource(R.string.game_details),
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.ExtraBold,
            modifier = Modifier.padding(bottom = 16.dp).fillMaxWidth()
        )

        HorizontalDivider(thickness = 4.dp)

        Text(
            text = (game?.maxCorrectLength ?: "0").toString() + " - " + (game?.sequence ?: stringResource(R.string.game_not_found)),
            modifier = Modifier.fillMaxWidth().padding(8.dp),
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold
        )
    }
}

@Preview
@Composable
fun GameDetailScreenPreview() {
    GameDetailScreen("AAA", listOf(GameResult(3, "RRRB", "AAA")), onExit = {})
}