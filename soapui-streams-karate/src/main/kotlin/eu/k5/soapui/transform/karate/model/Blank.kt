package eu.k5.soapui.transform.karate.model

class Blank : Statement() {
    
    override fun write(writer: ModelWriter): ModelWriter = writer.writeLine("")


}