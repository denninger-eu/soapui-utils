package eu.k5.soapui.transform.karate.model

import eu.k5.soapui.transform.karate.model.literals.Literal


class Assignment(
    private val left: Literal,
    private val right: Expression
) : Expression {
    override fun write(writer: ModelWriter): ModelWriter {
        return writer.write(left).write(" = ").write(right)
    }

}