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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ContextAmbient
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.viewModel
import androidx.navigation.NavController
import dog.shebang.voiceoflabor.ui.VoiceViewModel
import dog.shebang.voiceoflabor.ui.VoiceViewModelFactory
import dog.shebang.voiceoflabor.ui.theme.deepSkyBlue
import dog.shebang.voiceoflabor.ui.theme.gold
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview

@ExperimentalCoroutinesApi
@FlowPreview
@Composable
fun RecListScreen(navController: NavController) {
    val viewModel: VoiceViewModel = viewModel(
        factory = VoiceViewModelFactory(ContextAmbient.current)
    )

    val isRecording by viewModel.recordingMode.observeAsState(VoiceViewModel.RecordingMode.Stopping)

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
                    Icon(
                        asset = when (isRecording) {
                            is VoiceViewModel.RecordingMode.Stopping -> Icons.Default.Mic
                            is VoiceViewModel.RecordingMode.Recording -> Icons.Default.MicNone
                        }
                    )
                },
                onClick = {
                    when (isRecording) {
                        is VoiceViewModel.RecordingMode.Stopping -> viewModel.deployRecorder()
                        is VoiceViewModel.RecordingMode.Recording -> viewModel.stopRecorder()
                    }
                }
            )
        },
        bodyContent = {
            RecListContents(viewModel = viewModel)
        }
    )
}

@ExperimentalCoroutinesApi
@FlowPreview
@Composable
fun RecListContents(viewModel: VoiceViewModel) {
    val voiceList by viewModel.voiceList.observeAsState(emptyList())

    VoiceList(voiceList = voiceList) {
        when (it.state.playingMode) {
            is VoiceViewModel.PlayingMode.Stopping -> viewModel.deployPlayer(it.voice)
            is VoiceViewModel.PlayingMode.Playing -> viewModel.stopPlayer()
        }
    }
}

@ExperimentalCoroutinesApi
@Composable
fun VoiceList(voiceList: List<VoiceViewModel.UiModel>, onClick: (VoiceViewModel.UiModel) -> Unit) {

    LazyColumnFor(
        voiceList,
        contentPadding = PaddingValues(8.dp)
    ) { item ->

        VoiceCard(
            item = item,
            modifier = Modifier
                .padding(10.dp)
                .fillParentMaxWidth(),
            onClick = onClick
        )
    }

}

@ExperimentalCoroutinesApi
@Composable
fun VoiceCard(
    item: VoiceViewModel.UiModel,
    modifier: Modifier = Modifier,
    onClick: (VoiceViewModel.UiModel) -> Unit
) {

    Card(
        modifier = modifier,
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
                text = item.voice.uri.fileName
            )

            Icon(
                modifier = Modifier
                    .weight(0.5f)
                    .clickable(onClick = { onClick(item) }),
                asset = when (item.state.playingMode) {
                    is VoiceViewModel.PlayingMode.Stopping -> Icons.Default.PlayCircleOutline
                    is VoiceViewModel.PlayingMode.Playing -> Icons.Default.PauseCircleOutline
                }
            )
        }

    }

}