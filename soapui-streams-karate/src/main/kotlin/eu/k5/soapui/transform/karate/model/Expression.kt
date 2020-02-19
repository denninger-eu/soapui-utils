package eu.k5.soapui.transform.karate.model

import eu.k5.soapui.transform.karate.model.literals.VariableLiteral

interface Expression {
    fun write(writer: ModelWriter): ModelWriter

    companion object {
        fun newInstance(variable: VariableLiteral): Expression {
            return NewInstanceExpression(variable)
        }
    }
}

fun ModelWriter.write(expression: Expression): ModelWriter {
    expression.write(this)
    return this
}