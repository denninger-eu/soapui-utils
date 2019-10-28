package eu.k5.soapui.streams.model.rest

interface SuuRestService {
    var name: String?
    var description: String?

    fun addResource(resource: SuuResource)
}
