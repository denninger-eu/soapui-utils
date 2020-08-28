package eu.k5.soapui.transform.restassured.ast

import eu.k5.soapui.transform.ModelWriter

class StatementList(
    private val statements: List<Statement>
) : Segment {


    override fun write(writer: ModelWriter): ModelWriter {
        for (statement in statements) {
            statement.write(writer)
        }
        return writer
    }

    override val imports: List<String>
        get() = emptyList()

}