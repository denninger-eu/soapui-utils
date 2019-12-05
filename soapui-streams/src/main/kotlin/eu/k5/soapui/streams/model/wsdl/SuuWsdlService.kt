package eu.k5.soapui.streams.model.wsdl

interface SuuWsdlService {
    var name: String
    var description: String?

    val operations: List<SuuWsdlOperation>
}