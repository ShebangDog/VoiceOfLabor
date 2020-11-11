package dog.shebang.voiceoflabor

import android.content.Context
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import dog.shebang.voiceoflabor.data.VoiceRepository
import dog.shebang.voiceoflabor.data.service.voice.VoicePlayer
import dog.shebang.voiceoflabor.data.service.voice.VoiceRecorder
import dog.shebang.voiceoflabor.model.Voice
import kotlinx.coroutines.launch
import java.util.*

class MainViewModel @ViewModelInject constructor(
    context: Context,
    private val repository: VoiceRepository
) : ViewModel() {

    private val mutableRecordingMode = MutableLiveData(false)
    val recordingMode: LiveData<Boolean> = mutableRecordingMode

    private val mutablePlayerMode = MutableLiveData(false)
    val playerMode: LiveData<Boolean> = mutablePlayerMode

    private val voiceRecorder = VoiceRecorder(context).apply {
        setOnStop { mutableRecordingMode.value = false }
    }

    private val voicePlayer = VoicePlayer().apply {
        setOnStop { mutablePlayerMode.value = false }
    }

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