package eu.k5.soapui.transform.restassured.model.base

import eu.k5.soapui.transform.ModelWriter

class Statement(
    val expression: Expression
) {
    fun write(writer: ModelWriter) {
        writer.writeIndention()
        expression.write(writer)
        writer.write(";").newLine()
    }

}