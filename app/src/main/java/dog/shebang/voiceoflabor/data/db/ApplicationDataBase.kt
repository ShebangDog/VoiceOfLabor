package dog.shebang.voiceoflabor.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [VoiceEntity::class], version = 1)
@TypeConverters(value = [VoiceConverter::class])
abstract class ApplicationDataBase : RoomDatabase() {
    abstract fun voiceDao(): VoiceDao
}

object DataBaseBuilder {
    fun build(applicationContext: Context) = Room.databaseBuilder(
        applicationContext,
        ApplicationDataBase::class.java,
        "voice-of-labor-voice"
    ).build()

}
