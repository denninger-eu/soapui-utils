package eu.k5.soapui.transform.karate.model

class Match(
    val type: String,
    val value: String
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