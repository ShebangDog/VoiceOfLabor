package dog.shebang.voiceoflabor.data

import dog.shebang.voiceoflabor.data.db.ApplicationDataBase
import dog.shebang.voiceoflabor.data.db.InternalStorageDataSource
import dog.shebang.voiceoflabor.data.db.VoiceEntity
import dog.shebang.voiceoflabor.model.Voice
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

interface VoiceRepository {
    fun fetchVoice(fileName: String): Flow<Voice?>

    fun fetchVoiceList(): Flow<List<Voice>>

    suspend fun saveVoice(fileName: String)
}

class DefaultVoiceRepository(
    private val database: ApplicationDataBase,
    private val internalStorageDataSource: InternalStorageDataSource
) : VoiceRepository {

    @FlowPreview
    @ExperimentalCoroutinesApi
    override fun fetchVoice(fileName: String): Flow<Voice?> {
        val uri = internalStorageDataSource.formatToAccess(fileName)

        return database.voiceDao().fetch(uri).map { Voice(uri) }
    }

    @FlowPreview
    @ExperimentalCoroutinesApi
    override fun fetchVoiceList(): Flow<List<Voice>> = database.voiceDao().fetchAll()
        .map { list -> list.map { Voice(it.uri) } }

    override suspend fun saveVoice(fileName: String) {
        val uri = internalStorageDataSource.formatToAccess(fileName)

        database.voiceDao().insert(VoiceEntity(uri))
    }

}