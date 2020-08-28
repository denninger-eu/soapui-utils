package eu.k5.soapui.transform.restassured.ast.expression

import eu.k5.soapui.transform.ModelWriter
import eu.k5.soapui.transform.restassured.ast.Expression

class NewLine(
    private val expression: Expression,
    private val indent: Int = 0
) : Expression {

    override fun write(writer: ModelWriter): ModelWriter {
        writer.write(expression).newLine()
        writer.incIndent(indent)
        return writer.writeIndention()
    }

}