package eu.k5.soapui.transform.karate.model.literals

import eu.k5.soapui.transform.karate.ModelWriter

class ConstantLiteral(
    private val name: String
) : Literal {
    override fun write(writer: ModelWriter): ModelWriter {
        return writer.write(name)
    }
}