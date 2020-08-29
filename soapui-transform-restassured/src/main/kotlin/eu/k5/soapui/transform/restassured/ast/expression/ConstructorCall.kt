package eu.k5.soapui.transform.restassured.ast.expression

import eu.k5.soapui.transform.ModelWriter
import eu.k5.soapui.transform.restassured.ast.Expression

class ConstructorCall(
    private val name: String,
    private val arguments: List<Expression>
) : Expression {

    constructor(name: String) : this(name, ArrayList())

    override fun write(writer: ModelWriter): ModelWriter {
        writer.write("new ").write(name).write("(")
        for (argument in arguments.withIndex()) {
            writer.write(argument.value)
            if (argument.index < arguments.size - 1) {
                writer.write(", ")
            }
        }
        return writer.write(")")
    }

}