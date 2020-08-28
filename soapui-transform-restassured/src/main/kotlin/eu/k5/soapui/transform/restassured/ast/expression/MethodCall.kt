package eu.k5.soapui.transform.restassured.ast.expression

import eu.k5.soapui.transform.ModelWriter
import eu.k5.soapui.transform.restassured.ast.Expression
import eu.k5.soapui.transform.restassured.ast.Method

class MethodCall(
    private val method: Method
) : Expression {


    override fun write(writer: ModelWriter): ModelWriter {
        writer.write(method.name).write("()")
        return writer
    }


}