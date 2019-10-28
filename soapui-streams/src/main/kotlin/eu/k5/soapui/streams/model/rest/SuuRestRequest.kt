package eu.k5.soapui.streams.model.rest

interface SuuRestRequest {

    var name: String?
    var description: String?

    val parameters: MutableList<RestParameter>

}