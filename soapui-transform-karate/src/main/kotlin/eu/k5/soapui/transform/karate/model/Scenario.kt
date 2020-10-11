package eu.k5.soapui.transform.karate.model

import eu.k5.soapui.transform.ModelWriter
import eu.k5.soapui.transform.Writable
import eu.k5.soapui.transform.karate.model.statements.Blank
import eu.k5.soapui.transform.karate.model.statements.Star

class Scenario(
    val description: String
) : Writable {

    val backgrounds: MutableList<Statement> = ArrayList()

    val inits: MutableList<Statement> = ArrayList()

    val tags: MutableList<String> = ArrayList()

    val postbackground: MutableList<String> = ArrayList()

    val postexecute: MutableList<String> = ArrayList()

    val bodies: MutableList<Statement> = ArrayList()

    fun addStar(expression: Expression) {
        bodies.add(Star(expression))
    }

    override fun write(writer: ModelWriter): ModelWriter {
        if (tags.isNotEmpty()) {
            for (tag in tags) {
                writer.writeLine(tag)
            }
        }
        writer.writeLine("Feature: ", description)
        writer.newLine()


        writer.writeLine("Background: ")
        writer.incIndent()

        for (background in backgrounds) {
            background.write(writer)
        }
        writer.newLine()
        if (postbackground.isNotEmpty()) {
            writer.writeLine("# additional background start")
            for (postbackground in postbackground) {
                writer.writeLine(postbackground)
            }
            writer.writeLine("# additional background end")
            writer.newLine()
        }
        writer.decIndent()



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

        if (postexecute.isNotEmpty()) {
            writer.writeLine("# post start")
            for (pe in postexecute) {
                writer.writeLine(pe)
            }
            writer.writeLine("# post end")

        }
        writer.decIndent()
        return writer
    }
}