package dog.shebang.voiceoflabor.model

data class Uri(private val parentDirectory: String, val fileName: String) {
    init {
        require(fileName.contains('/').not())
    }

    val path = "$parentDirectory/$fileName"

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Uri

        if (fileName != other.fileName) return false
        if (path != other.path) return false

        return true
    }

    override fun hashCode(): Int {
        var result = fileName.hashCode()
        result = 31 * result + path.hashCode()
        return result
    }


}