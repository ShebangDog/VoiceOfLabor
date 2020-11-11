package dog.shebang.voiceoflabor.ui.viewmodel

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
        voiceRecorder.setOnStop { mutableIsRecording.value = false }

        voicePlayer.setOnStop { mutableIsPlaying.value = false }
    }

    private val mutableIsRecording = MutableLiveData(false)
    val isRecording: LiveData<Boolean> = mutableIsRecording

    private val mutableIsPlaying = MutableLiveData(false)
    val isPlaying: LiveData<Boolean> = mutableIsPlaying

    val voiceList = repository.fetchVoiceList().asLiveData()

    fun deployRecorder() {
        mutableIsRecording.value = true

        val fileName = UUID.randomUUID().toString()
        voiceRecorder.record(fileName)
    }

    fun stopRecorder() = viewModelScope.launch {
        mutableIsRecording.value = false

        voiceRecorder.stop()

        voiceRecorder.lastUri?.let { repository.saveVoice(it.fileName) }
    }

    fun deployPlayer(voice: Voice) {
        mutableIsPlaying.value = true

        voicePlayer.play(voice)
    }

    fun stopPlayer() {
        mutableIsPlaying.value = false

        voicePlayer.stop()
    }

}