package eu.k5.soapui.transform.karate.model

import eu.k5.soapui.transform.karate.ModelWriter

class ExternalFileExpression(
    private val name: String,
    private val content: String
) : Expression {

    override fun write(writer: ModelWriter): ModelWriter {
        writer.env.addFile(content)
        writer.write("read( ")
        writer.write(")")
        return writer
    }

}