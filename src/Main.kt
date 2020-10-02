import java.nio.file.Paths
import java.util.*
import kotlin.collections.ArrayList

val file = File("${Paths.get("").toAbsolutePath()}/src/res/test.txt")
val lexicTable = initLexicTable()
var lastWasBlankSpace = false

fun main(){
    val tokens = getValidWords()
    println(tokens.joinToString(", "))
    val analisadorSintactico = AnalisadorSintactico(tokens)
    print(analisadorSintactico.head)
}

fun getValidWords() : Array<String>{
    val arrayListValidos = ArrayList<String>()
    while (file.lastReadChar != File.EOF){
        val palabra = metodo()
        val isValid = isValid(palabra)
        if(isValid == 0) {
            arrayListValidos.add(palabra)
        }else if(isValid == 1){
            arrayListValidos.removeAt(arrayListValidos.lastIndex)
        }
    }

    return arrayListValidos.toTypedArray()
}

fun isValid(palabra: String) : Int{
    val lastWasBlankspaceCopy = lastWasBlankSpace
    if(palabra.isBlank()){
        lastWasBlankSpace = true
        return -1
    }else{
        lastWasBlankSpace = false
    }

    return if(lexicTable.contains(palabra)){
        0
    }else if(!lastWasBlankspaceCopy){
        1
    }else if(palabra.startsWith(24.toChar())){
        2
    }else{
        0
    }
}

fun initLexicTable() : Array<String>{
    val tableFile = Scanner(java.io.File("${Paths.get("").toAbsolutePath()}/src/res/tabla_simbolos.txt"))
    val arrayList = ArrayList<String>()

    while (tableFile.hasNextLine()){
        arrayList.add(tableFile.nextLine())
    }

    return arrayList.toTypedArray()
}

fun metodo() : String{
    val alpha = matchAlpha()
    if(alpha.isNotEmpty()){
        return alpha
    }

    val num = matchNumber()
    if(num.isNotEmpty()){
        return num
    }

    val symbol = matchSymbol()
    if(symbol.isNotEmpty() && lexicTable.contains(symbol)){
        return symbol
    }

    if(symbol.isBlank()) return symbol

    return "${24.toChar()}$symbol"
}

fun matchAlpha() : String{
    var res = ""
    while (file.lastReadChar != File.EOF){
        if(file.lastReadChar.isLetter() || file.lastReadChar == '_'){
            res += file.lastReadChar
            while (file.readChar().isLetter() || file.lastReadChar == '_' || file.lastReadChar.isDigit()){
                res += file.lastReadChar
            }
        }else{
            return res
        }
    }

    return res
}

fun matchSymbol() : String{
    var res = ""

    if(!(file.lastReadChar.isLetter() || file.lastReadChar.isDigit() || file.lastReadChar == '_')) {
        res += file.lastReadChar
        file.readChar()

        if(((res.first() == '|' || res.first() == '=' || res.first() == '&') && res.first() == file.lastReadChar) ||
            ((res.first() == '>' || res.first() == '<' || res.first() == '!') && file.lastReadChar == '=')) {
            res += file.lastReadChar
            file.readChar()
        }
    }

    return res
}

fun matchNumber() : String{
    var res = ""
    while (file.lastReadChar != File.EOF){
        if(file.lastReadChar.isDigit() || file.lastReadChar == '.') {
            res += file.lastReadChar
        }else{
            return res
        }

        file.readChar()
    }

    return res
}

