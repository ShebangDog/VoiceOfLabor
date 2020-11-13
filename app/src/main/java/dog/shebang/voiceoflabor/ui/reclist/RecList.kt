package dog.shebang.voiceoflabor.ui.reclist

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Text
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumnFor
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.MicNone
import androidx.compose.material.icons.filled.PauseCircleOutline
import androidx.compose.material.icons.filled.PlayCircleOutline
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ContextAmbient
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.viewModel
import androidx.navigation.NavController
import androidx.ui.tooling.preview.Preview
import dog.shebang.voiceoflabor.ui.VoiceViewModel
import dog.shebang.voiceoflabor.ui.VoiceViewModelFactory
import dog.shebang.voiceoflabor.ui.theme.deepSkyBlue
import dog.shebang.voiceoflabor.ui.theme.gold


@Composable
fun RecListScreen(navController: NavController) {
    val viewModel: VoiceViewModel = viewModel(
        factory = VoiceViewModelFactory(ContextAmbient.current)
    )

    val testList = listOf("Java", "Kotlin", "PHP", "Swift")
    val isRecording = remember { mutableStateOf(false) }

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
                icon = {
                    Icon(asset = if (isRecording.value) Icons.Default.MicNone else Icons.Default.Mic)
                },
                onClick = {
                    isRecording.value = !isRecording.value
                })
        },
        bodyContent = {

            ShowVoiceList(voiceList = testList)

        }
    )
}


@Composable
fun ShowVoiceList(voiceList: List<String>) {

    LazyColumnFor(
        voiceList,
        contentPadding = PaddingValues(8.dp)
    ) {
        val rowModifier = Modifier.padding(10.dp).fillParentMaxWidth()
        for (item in voiceList) {
            ShowVoiceCard(item = item, rowModifier)
        }
    }
}

@Composable
fun ShowVoiceCard(item: String, rowModifier: Modifier) {

    val isPlaying = remember { mutableStateOf(false) }

    Card(
        modifier = rowModifier,
        border = BorderStroke(color = Color.Black, width = Dp.Hairline),
        elevation = 4.dp,
        shape = RoundedCornerShape(8.dp)
    ) {

        Row(
            modifier = Modifier.padding(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier.weight(1f),
                text = item
            )

            Icon(
                modifier = Modifier.weight(0.5f).clickable(onClick = {
                    isPlaying.value = !isPlaying.value

                }),
                asset = if (isPlaying.value) Icons.Default.PauseCircleOutline else Icons.Default.PlayCircleOutline
            )
        }

    }

}

@Preview(showBackground = true)
@Composable
fun ShowPreview() {

    val testList = listOf("Java", "Kotlin", "PHP", "Swift")
    val isRecording = remember { mutableStateOf(false) }

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
                icon = {
                    Icon(asset = if (isRecording.value) Icons.Default.MicNone else Icons.Default.Mic)
                },
                onClick = {
                    isRecording.value = !isRecording.value
                })
        },
        bodyContent = {

            ShowVoiceList(voiceList = testList)

        }
    )

}