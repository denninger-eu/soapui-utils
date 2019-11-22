package eu.k5.soapui.streams.model.rest

interface SuuRestService {
    var name: String
    var description: String?
    var basePath: String
    val resources: List<SuuRestResource>

    fun getResource(name: String): SuuRestResource? = resources.firstOrNull { it.name == name }

    fun createResource(name: String, path: String): SuuRestResource
}
