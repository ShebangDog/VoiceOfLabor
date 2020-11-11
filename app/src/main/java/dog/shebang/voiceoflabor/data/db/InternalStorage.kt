package dog.shebang.voiceoflabor.data.db

import android.content.Context
import dog.shebang.voiceoflabor.model.Uri

interface InternalStorageDataSource {
    val internalDirectory: String

    fun formatToAccess(fileName: String): Uri
}

class DefaultInternalStorageDataSource(context: Context) : InternalStorageDataSource {
    override val internalDirectory: String = context.filesDir.path

    override fun formatToAccess(fileName: String): Uri = Uri(internalDirectory, fileName)
}