package eu.k5.soapui.transform.restassured.ast.literal

import eu.k5.soapui.transform.ModelWriter

class FieldLiteral(
    val name: String
) : Literal {

    override fun write(writer: ModelWriter): ModelWriter {
        return writer.write(name)
    }

}