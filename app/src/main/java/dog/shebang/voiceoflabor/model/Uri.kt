package dog.shebang.voiceoflabor.model

data class Uri(private val parentDirectory: String, val fileName: String) {
    init {
        require(fileName.contains('/').not())
    }

    val path = "$parentDirectory/$fileName"
}