package eu.k5.soapui.transform

interface Writable {
    fun write(writer: ModelWriter): ModelWriter
}