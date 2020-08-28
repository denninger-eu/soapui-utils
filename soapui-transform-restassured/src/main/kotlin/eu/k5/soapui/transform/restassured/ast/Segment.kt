package eu.k5.soapui.transform.restassured.ast

import eu.k5.soapui.transform.ModelWriter

interface Segment {

    fun write(writer: ModelWriter): ModelWriter

}