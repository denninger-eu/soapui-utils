package eu.k5.soapui.streams.model.wsdl

import eu.k5.soapui.streams.model.Header
import eu.k5.soapui.streams.model.WithHeader

interface SuuWsdlRequest : WithHeader {
    var name: String

    var description: String?
    var content: String

    fun markLostAndFound()

}
