package eu.k5.soapui.transform.karate.model

import eu.k5.soapui.transform.karate.ModelWriter
import eu.k5.soapui.transform.karate.model.statements.Star

class Scenario(
    val description: String
) {

    val statements: MutableList<Statement> = ArrayList()

    fun addStar(expression: Expression) {
        statements.add(Star(expression))
    }

    fun write(writer: ModelWriter) {
        writer.writeLine("Scenario: ", description)
        writer.incIndent()
        for (statement in statements) {
            statement.write(writer)
        }
        writer.decIndent()
    }
}