package dog.shebang.voiceoflabor.ui.reclist

import android.speech.tts.Voice
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ScrollableColumn
import androidx.compose.foundation.Text
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyColumnFor
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Mic
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.ui.tooling.preview.Preview
import dog.shebang.voiceoflabor.base.screen.Screen
import dog.shebang.voiceoflabor.theme.gold
import dog.shebang.voiceoflabor.theme.purple200
import dog.shebang.voiceoflabor.theme.purple500
import dog.shebang.voiceoflabor.theme.shapes


@Composable
fun RecListScreen(navController: NavController) {



    Scaffold(
            topBar = {
                TopAppBar(
                        title = { Text(text = "VoiceOfLabor") },
                        backgroundColor = purple500
                )
            },
            floatingActionButtonPosition = FabPosition.End,
            floatingActionButton = {
                FloatingActionButton(
                        backgroundColor = gold,
                        icon = { Icon(asset = Icons.Default.Mic) },
                        onClick = {})
            },
            bodyContent = {


            }
    )
}


@Composable
fun ShowVoiceList(voiceList: List<String>) {

    LazyColumnFor(
            voiceList,
    contentPadding = PaddingValues(8.dp)
    ){
        for (item in voiceList) {
            Card(
                    modifier = Modifier.padding(20.dp).fillParentMaxWidth(),
                    border = BorderStroke(color = Color.Black, width = Dp.Hairline),
                    shape = RoundedCornerShape(8.dp)
            ) {
                
                Text(text = item)

            }
        }
        }
    }

@Preview(showBackground = true)
@Composable
fun ShowPreview() {
    val testList = listOf("Java", "Kotlin", "PHP", "Swift")
    ShowVoiceList(voiceList = testList)
}