package eu.k5.soapui.transform.karate.model.literals

import eu.k5.soapui.transform.ModelWriter
import eu.k5.soapui.transform.karate.model.Expression

interface Literal : Expression {

    override fun write(writer: ModelWriter): ModelWriter

}

fun ModelWriter.write(literal: Literal): ModelWriter = literal.write(this)

