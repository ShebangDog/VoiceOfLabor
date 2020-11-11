package dog.shebang.voiceoflabor.data

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dog.shebang.voiceoflabor.data.db.ApplicationDataBase
import dog.shebang.voiceoflabor.data.db.voice.VoiceEntity
import dog.shebang.voiceoflabor.model.Voice
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

interface VoiceRepository {
    fun fetchVoice(fileName: String): Flow<Voice?>

    fun fetchVoiceList(): Flow<List<Voice>>

    suspend fun saveVoice(fileName: String)
}

class DefaultVoiceRepository @Inject constructor(
    private val database: ApplicationDataBase,
    private val internalStorageAccessor: InternalStorageAccessor
) : VoiceRepository {

    @FlowPreview
    @ExperimentalCoroutinesApi
    override fun fetchVoice(fileName: String): Flow<Voice?> {
        val uri = internalStorageAccessor.formatToAccess(fileName)

        return database.voiceDao().fetch(uri).map { Voice(uri) }
    }

    @FlowPreview
    @ExperimentalCoroutinesApi
    override fun fetchVoiceList(): Flow<List<Voice>> = database.voiceDao().fetchAll()
        .map { list -> list.map { Voice(it.uri) } }

    override suspend fun saveVoice(fileName: String) {
        val uri = internalStorageAccessor.formatToAccess(fileName)

        database.voiceDao().insert(VoiceEntity(uri))
    }

}

@Module
@InstallIn(ApplicationComponent::class)
abstract class VoiceRepositoryModule {

    @Binds
    abstract fun bindVoiceRepository(
        voiceRepository: DefaultVoiceRepository
    ): VoiceRepository
}