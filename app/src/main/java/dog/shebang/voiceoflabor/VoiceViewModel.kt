package dog.shebang.voiceoflabor

import androidx.lifecycle.*
import dog.shebang.voiceoflabor.data.VoiceRepository
import dog.shebang.voiceoflabor.data.service.voice.VoicePlayer
import dog.shebang.voiceoflabor.data.service.voice.VoiceRecorder
import dog.shebang.voiceoflabor.model.Voice
import kotlinx.coroutines.launch
import java.util.*

class VoiceViewModel(
    private val repository: VoiceRepository,
    private val voiceRecorder: VoiceRecorder,
    private val voicePlayer: VoicePlayer
) : ViewModel() {

    init {
        voiceRecorder.setOnStop { mutableIsRecording.value = RecordingMode.Stopping }

        voicePlayer.setOnStop { mutableIsPlaying.value = PlayingMode.Stopping }
    }

    private val mutableIsRecording = MutableLiveData<RecordingMode>(RecordingMode.Stopping)
    val recordingMode: LiveData<RecordingMode> = mutableIsRecording

    private val mutableIsPlaying = MutableLiveData<PlayingMode>(PlayingMode.Stopping)
    val playingMode: LiveData<PlayingMode> = mutableIsPlaying

    val voiceList = repository.fetchVoiceList().asLiveData()

    fun deployRecorder() {
        mutableIsRecording.value = RecordingMode.Recording

        val fileName = UUID.randomUUID().toString()
        voiceRecorder.record(fileName)
    }

    fun stopRecorder() = viewModelScope.launch {
        mutableIsRecording.value = RecordingMode.Stopping

        voiceRecorder.stop()
    }

    fun deployPlayer(voice: Voice) {
        mutableIsPlaying.value = PlayingMode.Playing(voice)

        voicePlayer.play(voice)
    }

    fun stopPlayer() {
        mutableIsPlaying.value = PlayingMode.Stopping

        voicePlayer.stop()
    }

    sealed class PlayingMode {
        object Stopping : PlayingMode()
        class Playing(val voice: Voice) : PlayingMode()
    }

    sealed class RecordingMode {
        object Stopping : RecordingMode()
        object Recording : RecordingMode()
    }
}