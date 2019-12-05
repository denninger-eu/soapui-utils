package eu.k5.soapui.streams.model.wsdl

interface SuuWsdlOperation {

    val name: String

    var description: String?

    val requests: List<SuuWsdlRequest>

}
