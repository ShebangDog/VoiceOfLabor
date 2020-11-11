package dog.shebang.voiceoflabor.data.db.voice

import androidx.room.TypeConverter
import dog.shebang.voiceoflabor.model.Uri

class VoiceConverter {
    @TypeConverter
    fun stringFromUri(uri: Uri): String {
        return uri.path
    }

    @TypeConverter
    fun stringToUri(string: String): Uri {
        val split = string.split("/")
        val fileName = split.last()
        val parentDir = split.dropLast(1).joinToString("/")

        return Uri(parentDir, fileName)
    }
}