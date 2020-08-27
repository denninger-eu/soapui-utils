package eu.k5.soapui.transform.karate.model.statements

import eu.k5.soapui.transform.ModelWriter
import eu.k5.soapui.transform.karate.model.Expression
import eu.k5.soapui.transform.karate.model.Statement
import eu.k5.soapui.transform.karate.model.write

class Star(
    val expression: Expression
) : Statement() {
    override fun write(writer: ModelWriter): ModelWriter {
        return writer.writeIndention().write("* ").write(expression).newLine()
    }

}