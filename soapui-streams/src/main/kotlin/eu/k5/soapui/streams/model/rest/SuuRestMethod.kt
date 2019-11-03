package eu.k5.soapui.streams.model.rest

import eu.k5.soapui.streams.jaxb.rest.RestParameter

interface SuuRestMethod {

    val parameters: MutableList<RestParameter>
    var name: String?
    var description: String?
    var method: HttpMethod?
    val requests: List<SuuRestRequest>


    fun getRequest(name: String): SuuRestRequest? = requests.firstOrNull { it.name == name }

    fun createRequest(name: String): SuuRestRequest

    enum class HttpMethod {
        GET, POST, PUT, DELETE, HEAD, PATCH, OPTIONS, TRACE,
    }
}