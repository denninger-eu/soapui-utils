package eu.k5.soapui.transform.karate.model.literals

import eu.k5.soapui.transform.ModelWriter


class JavaClassLiteral(
    private val className: String
) : Literal {
    override fun write(writer: ModelWriter): ModelWriter {
        writer.write("Java.type('")
        writer.write(
            className
        ).write("')")
        return writer
    }

}