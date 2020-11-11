package dog.shebang.voiceoflabor.data

import dog.shebang.voiceoflabor.model.Voice
import kotlinx.coroutines.flow.Flow

interface VoiceRepository {
    fun fetchVoice(fileName: String): Flow<Voice?>

    fun fetchVoiceList(): Flow<List<Voice>>

    suspend fun saveVoice(fileName: String)
}
