package dog.shebang.voiceoflabor.data.service.voice

import android.media.MediaPlayer
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dog.shebang.voiceoflabor.model.Voice
import javax.inject.Inject

typealias OnPlayerStopListener = () -> Unit

interface VoicePlayer {
    fun play(voice: Voice)

    fun stop()

    fun setOnStop(onStopListener: OnPlayerStopListener)

    fun removeOnStopListener()
}

class DefaultVoicePlayer @Inject constructor(

) : VoicePlayer {

    private var mediaPlayer: MediaPlayer? = null
    private var onStopListener: OnPlayerStopListener? = null

    override fun play(voice: Voice) {
        mediaPlayer = MediaPlayer()

        mediaPlayer?.apply {
            setOnPreparedListener {
                it.start()
            }

            setOnCompletionListener {
                this@DefaultVoicePlayer.stop()
            }

            setDataSource(voice.uri.path)
            prepareAsync()
        }
    }

    override fun stop() {
        mediaPlayer?.apply {
            stop()
            onStopListener?.invoke()
            release()
        }

        mediaPlayer = null
    }

    override fun setOnStop(onStopListener: OnPlayerStopListener) {
        this.onStopListener = onStopListener
    }

    override fun removeOnStopListener() {
        onStopListener = null
    }
}

@Module
@InstallIn(ApplicationComponent::class)
abstract class VoicePlayerModule {

    @Binds
    abstract fun bindVoicePlayer(
        voicePlayer: DefaultVoicePlayer
    ): VoicePlayer
}
