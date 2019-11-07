package eu.k5.soapui.streams.model.rest

import eu.k5.soapui.streams.jaxb.rest.RestParameter

interface SuuRestResource {

    var name: String?
    var description: String?
    var path: String?
    val parameters: MutableList<RestParameter>
    val methods: List<SuuRestMethod>
    val childResources: List<SuuRestResource>


    fun getChildResource(name: String): SuuRestResource? = childResources.find { it.name == name }

    fun getMethod(name: String): SuuRestMethod? = methods.first { it.name == name }

    fun createMethod(name: String): SuuRestMethod

    fun createChildResource(name: String, path: String): SuuRestResource

    //fun getOrCreateMethod(name: String): SuuRestMethod
    //  fun listChildResources(): List<SuuResource>

}
