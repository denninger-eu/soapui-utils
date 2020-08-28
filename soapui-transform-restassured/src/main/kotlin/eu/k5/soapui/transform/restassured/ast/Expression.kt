package eu.k5.soapui.transform.restassured.ast

import eu.k5.soapui.transform.ModelWriter
import eu.k5.soapui.transform.Writable

interface Expression : Writable {
    override fun write(writer: ModelWriter): ModelWriter
}