package eu.k5.soapui.streams.model.wsdl

interface SuuWsdlOperation {

    val name: String

    var description: String?

    val requests: List<SuuWsdlRequest>

    fun createRequest(name: String): SuuWsdlRequest

    fun getRequest(name: String): SuuWsdlRequest? = requests.firstOrNull { it.name == name }

    fun markLostAndFound()
}
