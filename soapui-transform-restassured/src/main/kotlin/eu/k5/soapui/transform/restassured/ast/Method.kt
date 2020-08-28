package eu.k5.soapui.transform.restassured.ast

import eu.k5.soapui.transform.ModelWriter

open class Method(
    val name: String,
    val body: Segment,
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
