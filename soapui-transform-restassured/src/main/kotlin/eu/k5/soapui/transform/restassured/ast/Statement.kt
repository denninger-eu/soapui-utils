package eu.k5.soapui.transform.restassured.ast

import eu.k5.soapui.transform.ModelWriter
import eu.k5.soapui.transform.Writable
import eu.k5.soapui.transform.restassured.ast.Expression

class Statement(
    val expression: Expression
) : Writable {
    override fun write(writer: ModelWriter): ModelWriter {
        return writer.writeIndention().write(expression).write(";").newLine()
    }

}
