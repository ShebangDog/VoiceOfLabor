package dog.shebang.voiceoflabor.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.setContent
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dog.shebang.voiceoflabor.base.screen.Screen
import dog.shebang.voiceoflabor.ui.reclist.RecListScreen
import dog.shebang.voiceoflabor.ui.recording.RecordingScreen
import dog.shebang.voiceoflabor.ui.theme.VoiceOfLaborTheme
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview

class MainActivity : AppCompatActivity() {
    @ExperimentalCoroutinesApi
    @FlowPreview
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            VoiceOfLaborApp()
        }
    }
}

@ExperimentalCoroutinesApi
@FlowPreview
@Composable
fun VoiceOfLaborApp() {
    VoiceOfLaborTheme(darkTheme = isSystemInDarkTheme()) {
        AppContent()
    }
}

@ExperimentalCoroutinesApi
@FlowPreview
@Composable
fun AppContent() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.RecList.name
    ) {

        composable(Screen.RecList.name) { RecListScreen(navController = navController) }
        composable(Screen.Recording.name) { RecordingScreen(navController = navController) }
    }
}