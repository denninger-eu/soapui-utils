package eu.k5.soapui.transform.restassured.model.base

import eu.k5.soapui.transform.ModelWriter
import eu.k5.soapui.transform.restassured.model.MethodBody

open class Method(
    val name: String,
    val body: MethodBody,
    private val visibility: Visibility = Visibility.PUBLIC

) {


    fun write(writer: ModelWriter) {
        writer.writeIndention().write(visibility.keyword).write(" void ").write(name).write("(){")
        writer.incIndent()
        writer.newLine()
        body.write(writer)
        writer.decIndent()
        writer.writeLine("}")
    }


}
