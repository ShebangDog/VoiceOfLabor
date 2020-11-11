package dog.shebang.voiceoflabor.data

import android.content.Context
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dog.shebang.voiceoflabor.model.Uri
import javax.inject.Inject

interface InternalStorageAccessor {
    val internalDirectory: String

    fun formatToAccess(fileName: String): Uri
}

class DefaultInternalStorageAccessor @Inject constructor(
    @ApplicationContext context: Context
) : InternalStorageAccessor {

    override val internalDirectory: String = context.filesDir.path

    override fun formatToAccess(fileName: String): Uri = Uri(internalDirectory, fileName)
}

@Module
@InstallIn(ApplicationComponent::class)
abstract class InternalStorageAccessorModule {

    @Binds
    abstract fun bindInternalStorageAccessor(
        internalStorageDataSource: DefaultInternalStorageAccessor
    ): InternalStorageAccessor

}