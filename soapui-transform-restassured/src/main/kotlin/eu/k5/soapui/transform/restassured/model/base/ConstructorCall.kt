package eu.k5.soapui.transform.restassured.model.base

import eu.k5.soapui.transform.ModelWriter

class ConstructorCall(
    private val name: String
) : Expression() {

    override fun write(writer: ModelWriter) {
        writer.write("new ").write(name).write("()")

    }

}