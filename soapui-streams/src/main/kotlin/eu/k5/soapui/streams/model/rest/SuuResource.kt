package eu.k5.soapui.streams.model.rest

interface SuuResource {

    var name: String?
    var description: String?
    var path: String?
    val parameters: MutableList<RestParameter>
    val methods: List<SuuRestMethod>
    val resources: List<SuuResource>


    fun getChildResource(name: String): SuuResource?

    fun getMethod(name: String): SuuRestMethod?

    fun createMethod(name: String): SuuRestMethod

    fun createChildResource(name: String, path: String): SuuResource

    //fun getOrCreateMethod(name: String): SuuRestMethod
    //  fun listChildResources(): List<SuuResource>

}
