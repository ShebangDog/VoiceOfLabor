package dog.shebang.voiceoflabor.base.screen

sealed class Screen(val name: String) {
    object RecList : Screen("reclist")
    object Recording : Screen("recording")
}