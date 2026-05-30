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

// Lista dei 6 colori del Simon
private val colors = listOf("R","G","B","Y","C","M")

// Durata in ms della visualizzazione/suono di un colore
private const val active_color_delay : Long = 1000

// Pausa in ms tra un colore e il successivo durante la riproduzione
private const val between_color_delay : Long = 300

// Intervallo di pausa in ms
private const val paused_delay : Long = 100

/**
 * ViewModel per la gestione della partita in corso.
 * Gestisce la logica di gioco, la riproduzione della sequenza CPU,
 * gli input dell'utente e la persistenza dello stato.
 * Il ciclo di vita è limitato alla sola GameScreen.
 *
 * @param savedStateHandle Handle per la persistenza dello stato tra distruzioni dell'activity
 */
class GameViewModel(private val savedStateHandle: SavedStateHandle) : ViewModel(){
    // Sequenza corretta generata e riprodotta dalla CPU
    private var _cpuSequence = mutableStateListOf<String>()
    val cpuSequence: List<String> = _cpuSequence
    // Sequenza di "click" dell'utente
    private var _userSequence = mutableStateListOf<String>()
    val userSequence: List<String> = _userSequence
    // Colore attivo usato per il feedback visivo sui bottoni
    private var _activeColor= mutableStateOf("")
    val activeColor: MutableState<String> = _activeColor

    private var playbackJob: Job? = null

    // Risultato della partita, generato al termine con endGame()
    private val _gameResult = mutableStateOf( GameResult(0, 0, "",""))
    val gameResult: State<GameResult> = _gameResult

    // Fase corrente di gioco, determina i bottoni attivi e il comportamento dell'UI
    private val _gamePhase = mutableStateOf(GamePhase.STATIC)
    val gamePhase: State<GamePhase> = _gamePhase

    // Indica se la riproduzione CPU è in pausa
    private val _isPaused = mutableStateOf(false)
    val isPaused: State<Boolean> = _isPaused

    // Ripristina lo stato della partita con SavedStateHandle (permette di sopravvivere alla distruzione dell'activity)
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

    /**
     * Avvia una nuova partita resettando lo stato precedente.
     * Cancella eventuali coroutine attive e avvia il primo turno.
     */
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

    /**
     * Gestisce il turno successivo della CPU.
     * Se [addColor] è true aggiunge un nuovo colore alla sequenza,
     * altrimenti riutilizza quella esistente (per ripristino dello stato).
     * Avvia la coroutine di riproduzione della sequenza con effetto visivo.
     *
     * @param addColor true per aggiungere un colore, false per riprodurre la sequenza esistente (dall'inizio)
     */
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

    /**
     * Cambia lo stato di pausa durante la riproduzione della sequenza CPU.
     */
    fun togglePause(){ _isPaused.value = !_isPaused.value }

    /**
     * Registra il colore premuto dall'utente e verifica se è quello corretto.
     * In caso di errore termina la partita, altrimenti se la sequenza
     * è completa passa al turno successivo.
     *
     * @param color Lettera del colore premuto
     */
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

    /**
     * Verifica se [_cpuSequence] e [_userSequence] corrispondono.
     *
     * @return true se l'ultimo colore inserito dall'utente non corrisponde a quello della sequenza CPU
     */
    private fun checkError() : Boolean {
        if(_cpuSequence[_userSequence.size-1] != _userSequence[_userSequence.size-1]) return true
        return false
    }

    /**
     * Termina la partita costruendo il [GameResult].
     * Gestisce 3 casi: errore del giocatore, uscita volontaria e
     * uscita durante la prima sequenza CPU (nessun dato salvato).
     * Cancella la coroutine di riproduzione e resetta il colore attivo.
     */
    fun endGame(){
        var seq = ""

        if (!(_gamePhase.value == GamePhase.CPU && _cpuSequence.size == 1)){
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

/**
 * Enum che rappresenta le fasi del gioco.
 * STATIC: gioco fermo,
 * CPU: riproduzione sequenza,
 * USER: fase gioco utente,
 * ERROR: errore commesso
 */
enum class GamePhase{ STATIC, CPU, USER, ERROR }