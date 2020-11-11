package dog.shebang.voiceoflabor.model

data class Voice(val uri: Uri) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Voice

        if (uri != other.uri) return false

        return true
    }

    override fun hashCode(): Int {
        return uri.hashCode()
    }
}

//you can get time of Voice with MediaMetaDataRetriever