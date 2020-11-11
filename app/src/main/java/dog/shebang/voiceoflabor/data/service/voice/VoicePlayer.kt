package dog.shebang.voiceoflabor.data.service.voice

import android.media.MediaPlayer
import dog.shebang.voiceoflabor.model.Voice

class VoicePlayer {
    private var mediaPlayer: MediaPlayer? = null
    private var onStopListener: (() -> Unit)? = null

    fun play(voice: Voice) {
        mediaPlayer = MediaPlayer()

        mediaPlayer?.apply {
            setOnPreparedListener {
                it.start()
            }

            setOnCompletionListener {
                this@VoicePlayer.stop()
            }

            setDataSource(voice.uri.path)
            prepareAsync()
        }
    }

    fun stop() {
        mediaPlayer?.apply {
            stop()
            onStopListener?.invoke()
            release()
        }

        mediaPlayer = null
    }

    fun setOnStop(listener: () -> Unit) {
        onStopListener = listener
    }

    fun removeListener() {
        onStopListener = null
    }
}
