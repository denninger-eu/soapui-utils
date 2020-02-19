package eu.k5.soapui.transform.karate.model.literals

import eu.k5.soapui.transform.karate.model.ModelWriter

class StringLiteral(
    val string: String
) : Literal {
    override fun write(writer: ModelWriter): ModelWriter {
        // TODO: escape string
        return writer.write("\"").write(string.replace("\"", "\\\"").replace("\n", "\\n").replace("\r", "\\r"))
            .write("\"")
    }

}