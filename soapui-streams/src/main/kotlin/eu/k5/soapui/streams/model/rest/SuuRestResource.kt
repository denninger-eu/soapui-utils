package eu.k5.soapui.streams.model.rest


interface SuuRestResource {


    var name: String
    var description: String?
    var path: String?
    val parameters: SuuRestParameters
    val methods: List<SuuRestMethod>
    val childResources: List<SuuRestResource>

    val parent: SuuRestResource?

    val allChildResources: Collection<SuuRestResource>
        get() {
            val result = ArrayList<SuuRestResource>()
            result.add(this)
            childResources.forEach { result.addAll(it.allChildResources) }
            return result
        }

    val fullPath: String?
        get() {
            var p = path
            if (p == null) {
                p = ""
            } else if (!p.startsWith("/")) {
                p = "/$p"
            }
            if (parent != null) {
                return parent!!.fullPath + p
            }
            return p
        }

    fun getResourcePath(): List<SuuRestResource> {
        val result = ArrayList<SuuRestResource>()
        if (parent != null) {
            result.addAll(parent!!.getResourcePath())
        }
        result.add(this)
        return result
    }

    fun getChildResource(name: String): SuuRestResource? = childResources.find { it.name == name }

    fun getMethod(name: String): SuuRestMethod? = methods.find { it.name == name }

    fun createMethod(name: String): SuuRestMethod

    fun createChildResource(name: String, path: String): SuuRestResource


    //fun getOrCreateMethod(name: String): SuuRestMethod
    //  fun listChildResources(): List<SuuResource>

}
