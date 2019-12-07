package eu.k5.soapui.streams.model.rest

import eu.k5.soapui.streams.model.WithHeader

interface SuuRestRequest : WithHeader {

    var name: String

    var description: String?

    val parameters: SuuRestParameters

    var content: String?


}