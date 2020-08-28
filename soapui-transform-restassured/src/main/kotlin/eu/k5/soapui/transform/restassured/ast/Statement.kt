package eu.k5.soapui.transform.restassured.ast

import eu.k5.soapui.transform.ModelWriter
import eu.k5.soapui.transform.restassured.ast.Expression

class Statement(
    val expression: Expression
) {
    fun write(writer: ModelWriter) {
        writer.writeIndention()
        expression.write(writer)
        writer.write(";").newLine()
    }

}
