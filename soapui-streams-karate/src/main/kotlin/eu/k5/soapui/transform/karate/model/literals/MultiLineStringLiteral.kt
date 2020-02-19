package eu.k5.soapui.transform.karate.model.literals

import eu.k5.soapui.transform.karate.model.ModelWriter

class MultiLineStringLiteral(private val value: String) : Literal {


    override fun write(writer: ModelWriter): ModelWriter {
        writer.write("\n\"\"\"\n")
        writer.write(value)
        return writer.write("\"\"\"")
    }

}