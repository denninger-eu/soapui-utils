package eu.k5.soapui.transform.karate.model.literals

import eu.k5.soapui.transform.karate.ModelWriter

class VariableLiteral(
    private val name: String
) : Literal {

    override fun write(writer: ModelWriter) = writer.write(name)

}