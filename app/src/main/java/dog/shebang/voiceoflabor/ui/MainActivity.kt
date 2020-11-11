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
import dog.shebang.voiceoflabor.theme.VoiceOfLaborTheme
import dog.shebang.voiceoflabor.ui.reclist.RecListScreen
import dog.shebang.voiceoflabor.ui.recording.RecordingScreen

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            VoiceOfLaborApp()
        }
    }
}

@Composable
fun VoiceOfLaborApp() {
    VoiceOfLaborTheme(darkTheme = isSystemInDarkTheme()) {
        AppContent()
    }
}

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