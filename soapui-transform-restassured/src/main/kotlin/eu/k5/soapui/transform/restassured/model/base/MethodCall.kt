package eu.k5.soapui.transform.restassured.model.base

import eu.k5.soapui.transform.ModelWriter

class MethodCall(
    val method: Method
) : Expression() {


    override fun write(writer: ModelWriter) {
        writer.write(method.name).write("()")
    }


}