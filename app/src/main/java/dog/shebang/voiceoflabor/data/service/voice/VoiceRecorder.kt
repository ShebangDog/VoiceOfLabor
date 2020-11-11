package dog.shebang.voiceoflabor.data.service.voice

import android.content.Context
import android.media.MediaRecorder
import dog.shebang.voiceoflabor.data.db.DefaultInternalStorageDataSource
import dog.shebang.voiceoflabor.model.Uri

class VoiceRecorder(context: Context) {
    private var mediaRecorder: MediaRecorder? = null
    private var onStopListener: (() -> Unit)? = null

    var lastUri: Uri? = null
        private set

    private val internalStorageDataSource = DefaultInternalStorageDataSource(context)

    fun record(fileName: String) {
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

    fun stop() {
        mediaRecorder?.apply {
            stop()
            release()
        }

        mediaRecorder = null
    }

    fun setOnStop(listener: () -> Unit) {
        onStopListener = listener
    }

    fun removeListener() {
        onStopListener = null
    }
}