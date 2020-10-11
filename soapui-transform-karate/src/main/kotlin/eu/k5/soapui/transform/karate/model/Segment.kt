package eu.k5.soapui.transform.karate.model

import eu.k5.soapui.transform.ModelWriter
import eu.k5.soapui.transform.Writable

interface Segment : Writable {

    override fun write(writer: ModelWriter): ModelWriter

}