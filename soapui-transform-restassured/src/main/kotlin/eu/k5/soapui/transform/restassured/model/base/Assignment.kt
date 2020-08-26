package eu.k5.soapui.transform.restassured.model.base

import eu.k5.soapui.transform.ModelWriter

class Assignment(
    private val left: Expression,
    private val right: Expression
) : Expression() {

    override fun write(writer: ModelWriter) {
        left.write(writer)
        writer.write(" = ")
        right.write(writer)
    }

}