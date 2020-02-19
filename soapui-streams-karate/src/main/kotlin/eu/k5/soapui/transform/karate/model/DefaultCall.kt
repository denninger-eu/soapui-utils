package eu.k5.soapui.transform.karate.model

class DefaultCall(val type: String, val value: String) : Expression {
    override fun write(writer: ModelWriter): ModelWriter {
        return writer.write(type).write(" ").write(value)
    }

    companion object {
        fun method(method: String): DefaultCall {
            return DefaultCall("method", method)
        }
    }
}