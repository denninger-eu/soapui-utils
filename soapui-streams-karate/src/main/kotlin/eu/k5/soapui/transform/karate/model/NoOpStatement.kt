package eu.k5.soapui.transform.karate.model

class NoOpStatement : Statement() {

    override fun write(writer: ModelWriter) : ModelWriter = writer

}