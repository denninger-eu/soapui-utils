package eu.k5.soapui.streams.model.rest


interface SuuRestMethod {

    val parameters: SuuRestParameters
    var name: String
    var description: String?
    var httpMethod: HttpMethod?
    val requests: List<SuuRestRequest>


    fun getRequest(name: String): SuuRestRequest? = requests.firstOrNull { it.name == name }

    fun createRequest(name: String): SuuRestRequest

    enum class HttpMethod {
        GET, POST, PUT, DELETE, HEAD, PATCH, OPTIONS, TRACE;

        companion object {
            fun forName(name: String): HttpMethod? = values().firstOrNull { it.name == name }
        }
    }
}