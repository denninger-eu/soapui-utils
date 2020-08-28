package eu.k5.soapui.transform.restassured.ast.literal

import eu.k5.soapui.transform.ModelWriter

class StringLiteral(
    private val string: String
) : Literal {

    override fun write(writer: ModelWriter): ModelWriter {
        return writer.write("\"").write(escaped()).write("\"")
    }

    private fun escaped(): String {
        // TODO: further escape string
        return string.replace("\"", "\\\"").replace("\n", "\\n").replace("\r", "\\r")
    }
}