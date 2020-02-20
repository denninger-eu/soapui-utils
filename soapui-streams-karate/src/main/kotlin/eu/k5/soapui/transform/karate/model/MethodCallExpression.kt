package eu.k5.soapui.transform.karate.model

import eu.k5.soapui.transform.karate.ModelWriter

class MethodCallExpression(
    private val instance: Expression,
    private val name: String,
    private val parameters: List<Expression>

) : Expression {
    constructor(instance: Expression, name: String) : this(instance, name, ArrayList())

    override fun write(writer: ModelWriter): ModelWriter {
        writer.write(instance).write(".").write(name).write("(")
        var first = true
        for (parameter in parameters.withIndex()) {
            writer.write(parameter.value)
            if (parameter.index + 1 < parameters.size) {
                writer.write(",")
            }
        }
        return writer.write(")")
    }

    fun chain(name: String, parameters: List<Expression>): MethodCallExpression {
        return MethodCallExpression(this, name, parameters)
    }

}