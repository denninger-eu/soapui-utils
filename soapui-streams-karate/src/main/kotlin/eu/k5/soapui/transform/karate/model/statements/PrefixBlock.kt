package eu.k5.soapui.transform.karate.model.statements

import eu.k5.soapui.transform.karate.model.Expression
import eu.k5.soapui.transform.karate.model.ModelWriter
import eu.k5.soapui.transform.karate.model.Statement

class PrefixBlock(
    val name: String,
    val additional: String = " And "
) : Statement() {

    val expressions = ArrayList<Expression>()

    override fun write(writer: ModelWriter): ModelWriter {
        if (expressions.isEmpty()) {
            return writer
        }
        writer.writeIndention().write(name)
        expressions[0].write(writer)
        writer.newLine()
        // writer.incIndent()
        for (index in 1 until expressions.size) {
            writer.writeIndention().write(additional)
            expressions[index].write(writer)
            writer.newLine()
        }
        // writer.decIndent()
        return writer
    }

    companion object {
        fun Given(): PrefixBlock =
            PrefixBlock("Given ", "  And ")
        fun When(): PrefixBlock =
            PrefixBlock("When  ", "  And ")
        fun Then(): PrefixBlock =
            PrefixBlock("Then  ", "  And ")
    }
}