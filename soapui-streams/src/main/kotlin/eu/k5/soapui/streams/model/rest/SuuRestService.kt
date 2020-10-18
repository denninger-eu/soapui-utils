package eu.k5.soapui.streams.model.rest

interface SuuRestService {
    var name: String
    var description: String?
    var basePath: String
    val resources: List<SuuRestResource>

    val endpoints: List<String>
    fun addEndpoint(endpoint: String)
    fun removeEndpoint(endpoint: String)

    fun getResource(name: String): SuuRestResource? = resources.firstOrNull { it.name == name }

    fun createResource(name: String, path: String): SuuRestResource


    val allResources: List<SuuRestResource>
        get() {
            val result = ArrayList<SuuRestResource>(resources)
            resources.forEach { result.addAll(it.allChildResources) }
            return result
        }


    fun getResourceByPath(path: String): SuuRestResource? = allResources.firstOrNull { it.fullPath == path }

}
