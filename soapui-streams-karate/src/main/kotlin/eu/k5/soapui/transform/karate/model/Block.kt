package eu.k5.soapui.transform.karate.model

import eu.k5.soapui.transform.karate.ModelWriter

class Block(name: String) : Statement() {

    constructor() : this("")

    val statements = ArrayList<Statement>()

    init {
        if (!name.isNullOrEmpty()) {
            statements.add(Comment(name))
        }
    }

    override fun write(writer: ModelWriter): ModelWriter {
        statements.forEach { it.write(writer) }
        return writer
    }

}