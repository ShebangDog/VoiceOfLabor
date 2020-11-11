package dog.shebang.voiceoflabor.data.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import dog.shebang.voiceoflabor.model.Uri

@Entity(tableName = "voices")
data class VoiceEntity(
    @PrimaryKey
    @ColumnInfo(name = "uri")
    val uri: Uri
)