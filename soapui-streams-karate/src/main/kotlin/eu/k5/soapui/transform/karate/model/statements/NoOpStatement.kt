package eu.k5.soapui.transform.karate.model.statements

import eu.k5.soapui.transform.karate.ModelWriter
import eu.k5.soapui.transform.karate.model.Statement

class NoOpStatement : Statement() {

    override fun write(writer: ModelWriter) : ModelWriter = writer

}