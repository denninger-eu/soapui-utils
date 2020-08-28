package eu.k5.soapui.transform.restassured.ast.expression

import eu.k5.soapui.transform.ModelWriter
import eu.k5.soapui.transform.restassured.ast.Expression
import eu.k5.soapui.transform.restassured.ast.Segment

class Declaration(
    val name: Reference,
    val type: String,
    val expression: Expression

) : Segment {

    override fun write(writer: ModelWriter): ModelWriter {
        return writer.writeIndention().write(type).write(" ").write(name).write(" = ").write(expression).write(";")
            .newLine()
    }

    override val imports: List<String>
        get() = emptyList()
}