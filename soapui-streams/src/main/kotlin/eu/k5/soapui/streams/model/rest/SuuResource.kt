package eu.k5.soapui.streams.model.rest

interface SuuResource {

    var name: String?
    var description: String?
    var path: String?
    val parameters: MutableList<RestParameter>
    val methods: List<SuuRestMethod>
    val resources: List<SuuResource>

    fun addResource(resource: SuuResource)

    fun addMethod(currentMethod: SuuRestMethod)

    fun getChildResource(name: String): SuuResource?

    fun getMethod(name: String): SuuRestMethod?

    fun createMethod(name: String): SuuRestMethod

    //fun getOrCreateMethod(name: String): SuuRestMethod
    //  fun listChildResources(): List<SuuResource>

}
