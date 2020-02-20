import java.io.FileReader

class File (src : String) {
    private val fileReader = FileReader(src)
    var lastReadChar : Char = 0.toChar()

    init {
        readChar()
    }

    companion object {
        const val EOF = (-1).toChar()
    }

    fun readChar() : Char{
        val char = fileReader.read().toChar()
        lastReadChar = char
        return char
    }
}