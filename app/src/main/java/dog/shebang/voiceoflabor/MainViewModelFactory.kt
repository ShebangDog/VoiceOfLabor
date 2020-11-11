package dog.shebang.voiceoflabor

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.EntryPointAccessors
import dagger.hilt.android.components.ApplicationComponent
import dog.shebang.voiceoflabor.data.VoiceRepository
import dog.shebang.voiceoflabor.data.service.voice.VoicePlayer
import dog.shebang.voiceoflabor.data.service.voice.VoiceRecorder

@Suppress("UNCHECKED_CAST")
class MainViewModelFactory(
    private val context: Context
) : ViewModelProvider.Factory {

    @EntryPoint
    @InstallIn(ApplicationComponent::class)
    interface MainViewModelFactoryEntryPoint {
        fun voiceRepository(): VoiceRepository

        fun voicePlayer(): VoicePlayer

        fun voiceRecorder(): VoiceRecorder
    }

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        val accessor = EntryPointAccessors
            .fromApplication(context, MainViewModelFactoryEntryPoint::class.java)

        val voiceRepository = accessor.voiceRepository()
        val voiceRecorder = accessor.voiceRecorder()
        val voicePlayer = accessor.voicePlayer()

        return MainViewModel(
            voiceRepository,
            voiceRecorder,
            voicePlayer
        ) as T
    }
}