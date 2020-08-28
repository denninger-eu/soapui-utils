package eu.k5.soapui.transform.restassured.ast.expression

import eu.k5.soapui.transform.ModelWriter
import eu.k5.soapui.transform.restassured.ast.Expression

class ConstructorCall(
    private val name: String
) : Expression {

    override fun write(writer: ModelWriter): ModelWriter {
        writer.write("new ").write(name).write("()")
        return writer
    }

}