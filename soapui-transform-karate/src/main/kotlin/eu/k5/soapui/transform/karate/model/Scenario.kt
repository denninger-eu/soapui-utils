package eu.k5.soapui.transform.karate.model

import eu.k5.soapui.transform.ModelWriter
import eu.k5.soapui.transform.Writable
import eu.k5.soapui.transform.karate.model.statements.Blank
import eu.k5.soapui.transform.karate.model.statements.Star

class Scenario(
    val description: String
) : Writable {

    val inits: MutableList<Statement> = ArrayList()

    val bodies: MutableList<Statement> = ArrayList()

    fun addStar(expression: Expression) {
        bodies.add(Star(expression))
    }

    override fun write(writer: ModelWriter): ModelWriter {
        writer.writeLine("Scenario: ", description)
        writer.incIndent()

        for (init in inits) {
            init.write(writer)
        }

        if (inits.isNotEmpty()) {
            writer.write(Blank())
        }

        for (statement in bodies) {
            statement.write(writer)
        }
        writer.decIndent()
        return writer
    }
}