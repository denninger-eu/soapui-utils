package eu.k5.soapui.transform.restassured.ast.literal

import eu.k5.soapui.transform.ModelWriter

class IntVarArgLiteral(
    private val ints: List<String>
) : Literal {
    override fun write(writer: ModelWriter): ModelWriter {
        return writer.write(ints.joinToString(", "))
    }

}