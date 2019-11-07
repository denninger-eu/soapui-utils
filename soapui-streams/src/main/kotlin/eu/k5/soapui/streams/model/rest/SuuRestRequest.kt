package eu.k5.soapui.streams.model.rest

import eu.k5.soapui.streams.jaxb.rest.RestParameter

interface SuuRestRequest {

    var name: String?
    var description: String?

    var content: String?
    
    val parameters: SuuRestParameters?

}