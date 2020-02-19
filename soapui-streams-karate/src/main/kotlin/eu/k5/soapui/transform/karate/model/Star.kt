package eu.k5.soapui.transform.karate.model

class Star(
    val expression: Expression
) : Statement() {
    override fun write(writer: ModelWriter): ModelWriter {
        return writer.writeIndention().write("* ").write(expression).newLine()
    }

}