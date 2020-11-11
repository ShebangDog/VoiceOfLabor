package dog.shebang.voiceoflabor.ui.recording

import androidx.compose.foundation.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import dog.shebang.voiceoflabor.base.screen.Screen

@Composable
fun RecordingScreen(navController: NavController) {

    Text(text = Screen.Recording.name)
}