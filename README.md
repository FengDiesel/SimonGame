## Progetto  di "Programmazione di sistemi embedded" - SimonGame
Il progetto è un'applicazione Android che funge da primo prototipo per il gioco "Simon".

# Ambiente di Sviluppo
L'applicazione è stata sviluppata e testata sul seguente ambiente:
**Dispositivo:** Emulatore Android
**Modello:** Pixel 2 (Android API 37.0 | x86_64)
**Versione Android (API Level):** 
**Minimum SDK:** API 24
**Target SDK:** API 36

# Struttura del progetto
L'applicazione è stata sviluppata utilizzando il framework **Jetpack Compose**. 

Alcuni dettagli sulle scelte adottate per soddisfare la consegna:

**Gestione dello Stato:** La persistenza dei dati durante i cambi di configurazione (es. rotazione dello schermo da Portrait a Landscape) è gestita tramite l'utilizzo di `rememberSaveable`. Lo storico delle partite risiede nel genitore (`MainActivity/NavHost`) per non perdere dati durante la navigazione.

**Layout Portrait/Landscape:** Il layout della schermata principale si adatta dinamicamente verificando lo stato della configurazione di orientamento attuale. La griglia dei colori è stata inserita in un composable a parte per mantenere la struttura 3x2, indipendentemente dall'orientamento.

**Navigazione:** Gestita tramite il componente `NavHost`. Il tasto "Back" di sistema dalla Schermata 2 (`FinalScreen.kt`) alla Schermata 1 (`HomeScreen.kt`) viene intercettato tramite il componente `BackHandler`.

**Lingue:** L'app supporta la lingua inglese (default) e italiana tramite la cartella `res/values-it`. Le sigle dei colori (R, G, B, Y, M, C) mostrate a schermo sono indipendenti dai file `strings.xml`.