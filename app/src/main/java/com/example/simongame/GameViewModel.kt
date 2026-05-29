package com.example.simongame

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.compose.runtime.State
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.UUID

private val colors = listOf("R","G","B","Y","C","M")
private const val active_color_delay : Long = 1000
private const val between_color_delay : Long = 300
private const val paused_delay : Long = 100

class GameViewModel(private val savedStateHandle: SavedStateHandle) : ViewModel(){
    private var _cpuSequence = mutableStateListOf<String>()
    val cpuSequence: List<String> = _cpuSequence
    private var _userSequence = mutableStateListOf<String>()
    val userSequence: List<String> = _userSequence
    private var _activeColor= mutableStateOf("")
    val activeColor: MutableState<String> = _activeColor

    private var playbackJob: Job? = null

    private val _gameResult = mutableStateOf( GameResult(0, 0, "",""))
    val gameResult: State<GameResult> = _gameResult

    private val _gamePhase = mutableStateOf(GamePhase.STATIC)
    val gamePhase: State<GamePhase> = _gamePhase

    private val _isPaused = mutableStateOf(false)
    val isPaused: State<Boolean> = _isPaused

    init {
        val savedCpu = savedStateHandle.get<ArrayList<String>>("cpu_sequence") ?: arrayListOf()
        val savedUser = savedStateHandle.get<ArrayList<String>>("user_sequence") ?: arrayListOf()
        val savedPhase = savedStateHandle.get<String>("game_phase") ?: GamePhase.STATIC.name

        _cpuSequence.addAll(savedCpu)
        _userSequence.addAll(savedUser)
        _gamePhase.value = GamePhase.valueOf(savedPhase)
        savedStateHandle["game_phase"] = _gamePhase.value.name

        if (_gamePhase.value == GamePhase.CPU) {
            nextTurn(false)
        }
    }

    fun startGame(){
        _cpuSequence.clear()
        _userSequence.clear()
        savedStateHandle["cpu_sequence"] = ArrayList(_cpuSequence)
        savedStateHandle["user_sequence"] = ArrayList(_userSequence)
        _activeColor.value = ""

        playbackJob?.cancel()

        _gamePhase.value = GamePhase.CPU
        savedStateHandle["game_phase"] = _gamePhase.value.name

        nextTurn(true)
    }

    private fun nextTurn(addColor: Boolean) {
        _userSequence.clear()
        savedStateHandle["user_sequence"] = ArrayList(_userSequence)

        if(addColor){
            _cpuSequence.add(colors.random())
            savedStateHandle["cpu_sequence"] = ArrayList(_cpuSequence)
        }

        _gamePhase.value = GamePhase.CPU
        savedStateHandle["game_phase"] = _gamePhase.value.name

        playbackJob = viewModelScope.launch{
            delay(1200)

            for (i in 1.._cpuSequence.size) {
                while(isPaused.value){
                    delay(paused_delay)
                }

                _activeColor.value = _cpuSequence[i-1]
                delay(active_color_delay)
                _activeColor.value = ""
                delay(between_color_delay)
            }
            _gamePhase.value = GamePhase.USER
            savedStateHandle["game_phase"] = _gamePhase.value.name
        }
    }

    fun togglePause(){ _isPaused.value = !_isPaused.value }

    fun clickedColor(color: String){
        _userSequence.add(color)
        savedStateHandle["user_sequence"] = ArrayList(_userSequence)

        if(checkError()) {
            _gamePhase.value = GamePhase.ERROR
            savedStateHandle["game_phase"] = _gamePhase.value.name
            endGame()
        }else if(_userSequence.size == _cpuSequence.size){
            nextTurn(true)
        }

    }

    private fun checkError() : Boolean {
        if(_cpuSequence[_userSequence.size-1] != _userSequence[_userSequence.size-1]) return true
        return false
    }

    fun endGame(){
        var seq = ""

        if (!(_gamePhase.value == GamePhase.CPU && _cpuSequence.size == 1)){ //non devo salvare dati se è in riproduzione la cpu e siamo nel primo round
            val currectSeq = when (_gamePhase.value) {
                GamePhase.ERROR -> userSequence.size - 1
                GamePhase.USER -> userSequence.size
                GamePhase.CPU -> _cpuSequence.size - 1
                else -> 0
            }

            seq = _cpuSequence.joinToString(" - ")

            _gameResult.value = GameResult(
                _cpuSequence.size-1,
                currectSeq,
                seq,
                UUID.randomUUID().toString()
            )

            if (_gamePhase.value != GamePhase.ERROR) {
                _gamePhase.value = GamePhase.STATIC
                savedStateHandle["game_phase"] = _gamePhase.value.name
            }
        } else { _gamePhase.value = GamePhase.STATIC; savedStateHandle["game_phase"] = _gamePhase.value.name}

        _activeColor.value = ""
        playbackJob?.cancel()
    }
}

enum class GamePhase{ STATIC, CPU, USER, ERROR }