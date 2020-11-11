package dog.shebang.voiceoflabor.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dog.shebang.voiceoflabor.data.db.voice.VoiceConverter
import dog.shebang.voiceoflabor.data.db.voice.VoiceDao
import dog.shebang.voiceoflabor.data.db.voice.VoiceEntity
import javax.inject.Singleton

@Database(entities = [VoiceEntity::class], version = 1)
@TypeConverters(value = [VoiceConverter::class])
abstract class ApplicationDataBase : RoomDatabase() {
    abstract fun voiceDao(): VoiceDao
}

@Module
@InstallIn(ApplicationComponent::class)
object ApplicationDataBaseModule {

    @Provides
    @Singleton
    fun build(@ApplicationContext context: Context): ApplicationDataBase =
        Room.databaseBuilder(
            context,
            ApplicationDataBase::class.java,
            "voice-of-labor-voice"
        ).build()

}
