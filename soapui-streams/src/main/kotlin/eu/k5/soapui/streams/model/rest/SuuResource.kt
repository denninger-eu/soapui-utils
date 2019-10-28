package eu.k5.soapui.streams.model.rest

interface SuuResource {

    var name: String?
    var path: String?
    val parameters: MutableList<RestParameter>

    fun addResource(resource: SuuResource)

    fun addMethod(currentMethod: SuuRestMethod)
}
