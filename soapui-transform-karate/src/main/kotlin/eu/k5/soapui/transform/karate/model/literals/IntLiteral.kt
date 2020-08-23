package eu.k5.soapui.transform.karate.model.literals

import eu.k5.soapui.transform.karate.ModelWriter

class IntLiteral(private val value: Int) : Literal {
    override fun write(writer: ModelWriter): ModelWriter {
        return writer.write(value.toString())
    }

}