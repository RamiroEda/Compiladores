import java.nio.file.Paths

/*
* Ramiro Estrada García
* 2015190034
* Gerardo Ayala Juarez
* 2015190005
*/

val file = File("${Paths.get("").toAbsolutePath()}/src/res/test.txt")

fun main(){
    println(metodo())
    println(metodo())
    println(metodo())
    println(metodo())
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
    if(symbol.isNotEmpty()){
        return symbol
    }

    return ""
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

