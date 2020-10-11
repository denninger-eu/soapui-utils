package eu.k5.soapui.transform.karate.model

import eu.k5.soapui.transform.ModelWriter


class MatchExpression(
    private val left: Expression,
    private val right: Expression
) : Expression {

    override fun write(writer: ModelWriter): ModelWriter {
        return writer.write("match ").write(left).write(" == ").write(right)
    }

    
}