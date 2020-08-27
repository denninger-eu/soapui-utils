package eu.k5.soapui.transform.karate.model

import eu.k5.soapui.transform.ModelWriter
import eu.k5.soapui.transform.karate.model.literals.VariableLiteral

class NewInstanceExpression(
    private val variable: VariableLiteral
) : Expression {

    override fun write(writer: ModelWriter): ModelWriter {
        writer.write("new ")
        variable.write(writer).write("()")
        return writer
    }

}
