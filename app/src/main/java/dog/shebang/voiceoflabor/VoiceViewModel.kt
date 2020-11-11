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
        voiceRecorder.setOnStop { mutableRecordingMode.value = false }

        voicePlayer.setOnStop { mutablePlayerMode.value = false }
    }

    private val mutableRecordingMode = MutableLiveData(false)
    val recordingMode: LiveData<Boolean> = mutableRecordingMode

    private val mutablePlayerMode = MutableLiveData(false)
    val playerMode: LiveData<Boolean> = mutablePlayerMode

    val voiceList = repository.fetchVoiceList().asLiveData()

    fun deployRecorder() {
        mutableRecordingMode.value = true

        val fileName = UUID.randomUUID().toString()
        voiceRecorder.record(fileName)
    }

    fun stopRecorder() = viewModelScope.launch {
        mutableRecordingMode.value = false

        voiceRecorder.stop()

        voiceRecorder.lastUri?.let { repository.saveVoice(it.fileName) }
    }

    fun deployPlayer(voice: Voice) {
        mutablePlayerMode.value = true

        voicePlayer.play(voice)
    }

    fun stopPlayer() {
        mutablePlayerMode.value = false

        voicePlayer.stop()
    }

}