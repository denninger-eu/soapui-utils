package eu.k5.soapui.transform.karate.model

import java.io.Writer

abstract class Statement {
    abstract fun write(writer: ModelWriter): ModelWriter
}

fun ModelWriter.write(statement: Statement): ModelWriter {
    statement.write(this)
    return this
}