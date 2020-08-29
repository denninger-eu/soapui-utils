package eu.k5.soapui.transform.restassured.ast.expression

import eu.k5.soapui.transform.ModelWriter
import eu.k5.soapui.transform.restassured.ast.Expression
import eu.k5.soapui.transform.restassured.ast.literal.Literal

class Reference(
    private val name: String
) : Literal {

    override fun write(writer: ModelWriter): ModelWriter = writer.write(name)

}