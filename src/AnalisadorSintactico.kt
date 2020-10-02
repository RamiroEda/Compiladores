class AnalisadorSintactico (private val tokens : Array<String>) {
    var currentToken = 0
    lateinit var head : Nodo
    lateinit var currentEvalNodo : Nodo

    init {
        programa()
        println("Analisis correcto")
    }

    private fun nextToken() : String?{
        val index = currentToken++

        return if(index < tokens.size){
            tokens[index]
        }else{
            null
        }
    }

    private fun currentTokenValue() : String{
        return if(currentToken-1 < tokens.size){
            tokens[currentToken-1]
        }else{
            ""
        }
    }

    private fun programa(){
        var token = nextToken()
        head = Nodo("PROGRAMA")
        while (token != null){
            currentEvalNodo = head
            nodeToChild()
            when(token) {
                "VAR" -> vVar()
                "PROC" -> proc()
                else -> throw Exception("PROGRAMA -> Debe iniciar con VAR o PROC")
            }
            token = nextToken()
        }
    }

    private fun vVar(){
        aux2()
    }

    private fun aux2(){
        if(nextToken() == "IDENT"){
            nodeToChild()
            
            aux3()
        }else{
            throw Exception("VAR -> Debe ser seguido por IDENT")
        }
    }

    private fun aux3(){
        val token = nextToken()
        
        if(token == ","){
            nodeToChild()
            
            aux2()
        }else if (token != ";"){
            throw Exception("VAR -> Debe terminar en ;")
        }

        currentEvalNodo.children.add(Nodo(currentTokenValue()))
    }

    private fun proc(){
        if (nextToken() == "IDENT"){
            nodeToChild()
            
            if (nextToken() == ":"){
                nodeToChild()
                
                proposicion()
            }else{
                throw Exception("PROC -> Debe ser seguido por :")
            }
        }else{
            throw Exception("PROC -> Debe ser seguido por IDENT")
        }

        if (nextToken() != ";"){
            throw Exception("PROC -> Debe terminar en ;")
        }
    }

    private fun proposicion(){
        when(nextToken()){
            "IDENT" -> {
                nodeToChild()
                
                if (nextToken() == "="){
                    nodeToChild()
                    
                    aux5()
                }else{
                    throw Exception("IDENT -> Debe ser seguido por =")
                }
            }
            "LLAMAR" -> {
                nodeToChild()
                
                if (nextToken() != "IDENT"){
                    throw Exception("LLAMAR -> Debe ser seguido por IDENT")
                }
                nodeToChild()
                
            }
            "LEER" -> {
                nodeToChild()
                
                if (nextToken() != "IDENT"){
                    throw Exception("LEER -> Debe ser seguido por IDENT")
                }
                nodeToChild()
                
            }
            "ESCRIBIR" -> {
                nodeToChild()
                
                aux5()
            }
            "CAMBIAR" -> {
                nodeToChild()
                
                if (nextToken() == "IDENT"){
                    nodeToChild()
                    
                    aux6()
                }else{
                    throw Exception("CAMBAIR -> Debe ser seguido por IDENT")
                }
            }
            else -> throw Exception("PROC -> Valor no valido para Proposicion")
        }
    }

    private fun aux5(){
        val token = nextToken()
        nodeToChild()
        
        if(token != "IDENT" && token != "NUM"){
            throw Exception("IDENT -> Debe ser seguido de IDENT o NUM")
        }
    }
    
    private fun nodeToChild(){
        currentEvalNodo.children.add(Nodo(currentTokenValue()))
        currentEvalNodo = currentEvalNodo.children.last()
    }

    private fun aux6(){
        if(nextToken() == "CASO"){
            nodeToChild()
            aux5()
            if (nextToken() == ":"){
                nodeToChild()
                
                proposicion()
                if (nextToken() == ";"){
                    nodeToChild()
                    
                    aux6()
                }else{
                    throw Exception("VAR -> Debe terminar en ;")
                }

                currentEvalNodo.children.add(Nodo(currentTokenValue()))
            }else{
                throw Exception("CAMBAIR -> Debe ser seguido por :")
            }
        }
    }
}