package eu.k5.soapui.transform.restassured.ast.expression

import eu.k5.soapui.transform.ModelWriter
import eu.k5.soapui.transform.restassured.ast.Expression

class Assignment(
    private val left: Expression,
    private val right: Expression
) : Expression {

    override fun write(writer: ModelWriter): ModelWriter {
        return writer.write(left).write(" = ").write(right)
    }

}