package com.example.simongame

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.compose.runtime.State
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.UUID

private val colors = listOf("R","G","B","Y","C","M")
private const val active_color_delay : Long = 1000
private const val between_color_delay : Long = 300
private const val paused_delay : Long = 100

class GameViewModel : ViewModel(){
    private var cpuSequence = mutableStateListOf<String>()
    private var _userSequence = mutableStateListOf<String>()
    val userSequence: List<String> = _userSequence
    private var _activeColor= mutableStateOf("")
    val activeColor: MutableState<String> = _activeColor

    private var playbackJob: Job? = null

    private val _gameResult = mutableStateOf( GameResult(0, "",""))
    val gameResult: State<GameResult> = _gameResult

    private val _gamePhase = mutableStateOf(GamePhase.STATIC)
    val gamePhase: State<GamePhase> = _gamePhase

    private val _isPaused = mutableStateOf(false)
    val isPaused: State<Boolean> = _isPaused

    fun startGame(){
        cpuSequence.clear()
        _userSequence.clear()

        _gamePhase.value = GamePhase.CPU

        nextTurn()
    }

    private fun nextTurn() {
        _userSequence.clear()
        cpuSequence.add(colors.random())

        playbackJob = viewModelScope.launch{
            for (i in 1..cpuSequence.size) {
                while(isPaused.value){
                    delay(paused_delay)
                }

                _activeColor.value = cpuSequence[i-1]
                delay(active_color_delay)
                _activeColor.value = ""
                delay(between_color_delay)
            }
            _gamePhase.value = GamePhase.USER
        }
    }

    fun togglePause(){ _isPaused.value = !_isPaused.value }

    fun clickedColor(color: String){
        _userSequence.add(color)

        if(checkError()) {
            _gamePhase.value = GamePhase.ERROR
            endGame()
        }else if(_userSequence.size == cpuSequence.size){
            nextTurn()
        }

    }

    private fun checkError() : Boolean {
        if(cpuSequence[_userSequence.size-1] != _userSequence[_userSequence.size-1]) return true
        return false
    }

    fun endGame(){
        var seq : String = ""

        if (!(_gamePhase.value == GamePhase.CPU && cpuSequence.size == 1)){ //non devo salvare dati se è in riproduzione la cpu e siamo nel primo round
            if(_gamePhase.value == GamePhase.ERROR){
                seq = _userSequence.joinToString(" - ")
            }else if(_userSequence.isEmpty()){
                seq = "X"
            }else{
                seq = _userSequence.joinToString(" - ") + " - X"
            }

            _gameResult.value = GameResult(
                cpuSequence.size-1,
                seq,
                UUID.randomUUID().toString()
            )
        }

        _gamePhase.value = GamePhase.STATIC
        playbackJob?.cancel()
    }
}

enum class GamePhase{ STATIC, CPU, USER, ERROR }