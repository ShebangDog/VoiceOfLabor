package dog.shebang.voiceoflabor.base.screen

import androidx.navigation.NavController
import androidx.navigation.compose.navigate

sealed class Screen(val name: String) {
    object RecList : Screen("reclist")
    object Recording : Screen("recording")
}

fun NavController.navigateToRecList() = navigate(Screen.RecList.name)
fun NavController.navigateToRecording() = navigate(Screen.Recording.name)
