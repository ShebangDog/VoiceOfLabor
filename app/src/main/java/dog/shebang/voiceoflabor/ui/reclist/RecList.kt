package dog.shebang.voiceoflabor.ui.reclist

import androidx.compose.foundation.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import dog.shebang.voiceoflabor.base.screen.Screen

@Composable
fun RecListScreen(navController: NavController) {

    Text(text = Screen.RecList.name)
}