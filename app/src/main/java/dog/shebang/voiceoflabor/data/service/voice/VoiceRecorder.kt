package dog.shebang.voiceoflabor.data.service.voice

import android.content.Context
import android.media.MediaRecorder
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dog.shebang.voiceoflabor.data.DefaultInternalStorageDataSource
import dog.shebang.voiceoflabor.model.Uri
import javax.inject.Inject

typealias OnRecorderStopListener = () -> Unit

interface VoiceRecorder {

    var lastUri: Uri?

    fun record(fileName: String)

    fun stop()

    fun setOnStop(onStopListener: OnRecorderStopListener)

    fun removeOnStopListener()
}

class DefaultVoiceRecorder @Inject constructor(
    @ApplicationContext context: Context
) : VoiceRecorder {

    private var mediaRecorder: MediaRecorder? = null
    private var onStopListener: (() -> Unit)? = null

    override var lastUri: Uri? = null

    private val internalStorageDataSource = DefaultInternalStorageDataSource(context)

    override fun record(fileName: String) {
        mediaRecorder = MediaRecorder()

        val uri = internalStorageDataSource.formatToAccess(fileName)

        mediaRecorder?.apply {
            setAudioSource(MediaRecorder.AudioSource.MIC)
            setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP)
            setOutputFile(uri.path)

            setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB)

            prepare()
            start()
        }

        lastUri = uri
    }

    override fun stop() {
        mediaRecorder?.apply {
            stop()
            release()
        }

        mediaRecorder = null
    }

    override fun setOnStop(onStopListener: () -> Unit) {
        this.onStopListener = onStopListener
    }

    override fun removeOnStopListener() {
        onStopListener = null
    }
}

@Module
@InstallIn(ApplicationComponent::class)
abstract class VoiceRecorderModule {

    @Binds
    abstract fun bindVoiceRecorder(
        voiceRecorder: DefaultVoiceRecorder
    ): VoiceRecorder
}
