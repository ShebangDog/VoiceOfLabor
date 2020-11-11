package dog.shebang.voiceoflabor.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import dog.shebang.voiceoflabor.model.Uri
import kotlinx.coroutines.flow.Flow

@Dao
interface VoiceDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(voiceEntity: VoiceEntity)

    @Query(value = "SELECT * FROM voices")
    fun fetchAll(): Flow<List<VoiceEntity>>

    @Query(value = "SELECT * FROM voices WHERE uri = :uri")
    fun fetch(uri: Uri): Flow<VoiceEntity>

    @Query("DELETE FROM voices WHERE uri = :uri")
    suspend fun delete(uri: Uri)
}
