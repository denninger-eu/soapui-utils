package eu.k5.soapui.transform.karate.model.statements

import eu.k5.soapui.transform.ModelWriter
import eu.k5.soapui.transform.karate.model.Statement

class Blank : Statement() {

    override fun write(writer: ModelWriter): ModelWriter = writer.writeLine("")


}