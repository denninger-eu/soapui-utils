package eu.k5.soapui.streams.model.rest

interface SuuRestMethod {

    val parameters: MutableList<RestParameter>
    var name: String?
    var description: String?
    var method: HttpMethod?

    fun addRequest(request: SuuRestRequest)

    enum class HttpMethod {
        GET, POST, PUT, DELETE, HEAD, PATCH, OPTIONS, TRACE,
    }
}