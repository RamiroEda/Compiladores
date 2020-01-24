import java.io.FileReader

class File (src : String) {
    private val fileReader = FileReader(src)

    fun lastChar() = fileReader.read().toChar()
}