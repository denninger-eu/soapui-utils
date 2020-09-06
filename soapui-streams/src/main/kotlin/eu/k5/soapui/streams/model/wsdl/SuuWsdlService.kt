package eu.k5.soapui.streams.model.wsdl

interface SuuWsdlService {

    var name: String
    var description: String?

    val operations: List<SuuWsdlOperation>

    val definition: SuuWsdlDefinition

    fun getOperation(name: String): SuuWsdlOperation? = operations.firstOrNull { it.name == name }

    fun createOperation(name: String): SuuWsdlOperation

    fun markLostAndFound()

}