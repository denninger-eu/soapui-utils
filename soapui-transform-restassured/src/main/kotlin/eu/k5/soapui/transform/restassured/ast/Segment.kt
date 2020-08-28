package eu.k5.soapui.transform.restassured.ast

import eu.k5.soapui.transform.ModelWriter
import eu.k5.soapui.transform.Writable

interface Segment : Writable {

    override fun write(writer: ModelWriter): ModelWriter

    val imports: List<String>

}