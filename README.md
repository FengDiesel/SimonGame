# Progetto  di "Programmazione di sistemi embedded" - SimonGame
Il progetto è un'applicazione Android che costituisce un prototipo funzionante del gioco "Simon".

## Ambiente di Sviluppo
L'applicazione è stata sviluppata e testata sul seguente ambiente:
- **Dispositivo:** Emulatore Android
- **Modello:** Pixel 2 (Android API 37.0 | x86_64)
- **Versione Android (API Level):** 
    - **Minimum SDK:** API 24
    - **Target SDK:** API 36

## Struttura del progetto
L'applicazione è stata sviluppata utilizzando il framework **Jetpack Compose**. 

### Schermate
- **StatsScreen**: lista delle partite concluse con sequenza colorata in verde/rosso
- **GameScreen**: schermata di gioco con matrice 3x2, feedback visivo/uditivo e controlli
- **GameDetailScreen**: dettaglio di una singola partita

### Navigazione
Gestita tramite il componente `NavHost` con tre destinazioni: `statsscreen`, `gamescreen` e `gamedetailscreen/{gameID}`.
Il passaggio del `gameID` alla schermata di dettaglio avviene tramite route con argomenti.

### Gestione dello Stato
- **Cambi di configurazione:** gestiti con `ViewModel` che sopravvive alla ricreazione dell'activity
- **Distruzione activity da parte di android:** gestito tramite `SavedStateHandle` nel `GameViewModel`
- **Chiusura dell'app:** la partita in corso non viene salvata
- **Persistenza lista partite:** gestita tramite database **Room**, i dati sopravvivono alla chiusura e al riavvio del dispositivo

### Layout Portrait/Landscape
Il layout della schermata principale si adatta dinamicamente verificando lo stato della configurazione di orientamento attuale.
- **Portrait:** griglia sopra, area testo e bottoni sotto.
- **Landscape:** griglia a sinistra, area testo e bottoni a destra.

### Feedback
- **Visivo**: il bottone attivo cambia durante la riproduzione
- **Uditivo**: gestito tramite `SoundPool` con file audio mp3

### Lingue
L'app supporta la lingua inglese (default) e italiana tramite la cartella `res/values-it`. Le sigle dei colori (R, G, B, Y, M, C) mostrate a schermo sono indipendenti dai file `strings.xml`.

