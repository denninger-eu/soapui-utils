package eu.k5.soapui.transform.restassured.model.base

import eu.k5.soapui.transform.ModelWriter
import eu.k5.soapui.transform.restassured.model.base.Expression

class FieldAccessExpression(
    private val name: String
) : Expression() {

    override fun write(writer: ModelWriter) {
        writer.write("this.").write(name)
    }

}