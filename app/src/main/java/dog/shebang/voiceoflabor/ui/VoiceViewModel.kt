package dog.shebang.voiceoflabor.ui

import androidx.lifecycle.*
import dog.shebang.voiceoflabor.data.VoiceRepository
import dog.shebang.voiceoflabor.data.service.voice.VoicePlayer
import dog.shebang.voiceoflabor.data.service.voice.VoiceRecorder
import dog.shebang.voiceoflabor.model.Voice
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import java.util.*

@ExperimentalCoroutinesApi
class VoiceViewModel(
    private val repository: VoiceRepository,
    private val voiceRecorder: VoiceRecorder,
    private val voicePlayer: VoicePlayer
) : ViewModel() {

    init {
        voiceRecorder.setOnStop { mutableIsRecording.value = RecordingMode.Stopping }

        voicePlayer.setOnStop { playingVoiceFlow.value = null }
    }

    data class UiModel(val voice: Voice, val state: State)

    data class State(val playingMode: PlayingMode)

    private val mutableIsRecording = MutableLiveData<RecordingMode>(RecordingMode.Stopping)
    val recordingMode: LiveData<RecordingMode> = mutableIsRecording

    @ExperimentalCoroutinesApi
    private val playingVoiceFlow = MutableStateFlow<Voice?>(null)

    @ExperimentalCoroutinesApi
    @FlowPreview
    val voiceList =
        repository.fetchVoiceList().combine(playingVoiceFlow) { voiceList, playingVoice ->
            voiceList.map { voice ->
                val playingMode = when (playingVoice) {
                    voice -> PlayingMode.Playing
                    else -> PlayingMode.Stopping
                }

                val state = State(playingMode)

                UiModel(voice, state)
            }
        }.asLiveData()

    fun deployRecorder() {
        mutableIsRecording.value = RecordingMode.Recording

        val fileName = UUID.randomUUID().toString()
        voiceRecorder.record(fileName)
    }

    fun stopRecorder() = viewModelScope.launch {
        mutableIsRecording.value = RecordingMode.Stopping

        voiceRecorder.lastUri?.fileName?.let { repository.saveVoice(it) }
        voiceRecorder.stop()
    }

    fun deployPlayer(voice: Voice) {
        stopPlayer()
        playingVoiceFlow.value = voice

        voicePlayer.play(voice)
    }

    fun stopPlayer() {
        playingVoiceFlow.value = null

        voicePlayer.stop()
    }

    sealed class PlayingMode {
        object Stopping : PlayingMode()
        object Playing : PlayingMode()
    }

    sealed class RecordingMode {
        object Stopping : RecordingMode()
        object Recording : RecordingMode()
    }
}