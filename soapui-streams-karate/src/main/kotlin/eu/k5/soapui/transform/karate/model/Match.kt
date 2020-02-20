package eu.k5.soapui.transform.karate.model

import eu.k5.soapui.transform.karate.ModelWriter

class Match(
    private val type: String,
    private val value: String
) : Expression {

    override fun write(writer: ModelWriter): ModelWriter {
        return writer.write(type).write(" ").write(value)
    }

    companion object {
        fun match() {

        }

        fun status(status: String): Expression {
            return Match("status", status)
        }
    }

}