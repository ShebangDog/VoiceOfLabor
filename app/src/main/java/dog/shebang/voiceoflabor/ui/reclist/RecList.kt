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
import androidx.compose.material.icons.filled.PlayCircleOutline
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.ui.tooling.preview.Preview
import dog.shebang.voiceoflabor.base.screen.Screen
import dog.shebang.voiceoflabor.theme.*


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
        val rowModifier = Modifier.padding(10.dp).fillParentMaxWidth()
        for (item in voiceList) {
            ShowVoiceCard(item = item, rowModifier)
        }
        }
    }

@Composable
fun ShowVoiceCard(item: String, rowModifier: Modifier) {

    Card(
            modifier = rowModifier,
            border = BorderStroke(color = Color.Black, width = Dp.Hairline),
            elevation = 4.dp,
            shape = RoundedCornerShape(8.dp)
    ) {

        Row(
                modifier = Modifier.padding(10.dp),
                verticalAlignment= Alignment.CenterVertically
        ) {
            Text(
                    modifier = Modifier.weight(1f),
                    text = item
            )

            Icon(
                    modifier = Modifier.weight(0.5f),
                    asset = Icons.Default.PlayCircleOutline)
        }

    }

}

@Preview(showBackground = true)
@Composable
fun ShowPreview() {

    val testList = listOf("Java", "Kotlin", "PHP", "Swift")

    Scaffold(
            topBar = {
                TopAppBar(
                        title = { Text(text = "VoiceOfLabor") },
                        backgroundColor = deepSkyBlue
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

                ShowVoiceList(voiceList = testList)

            }
    )

}