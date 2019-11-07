package eu.k5.soapui.streams.direct.model

import com.eviware.soapui.impl.rest.RestResource
import eu.k5.soapui.streams.jaxb.rest.RestParameter
import eu.k5.soapui.streams.model.rest.SuuRestResource
import eu.k5.soapui.streams.model.rest.SuuRestMethod

class ResourceDirect(
    private val resource: RestResource
) : SuuRestResource {



    override var name: String? = resource.name
    override var path: String? = resource.path
    override var description: String? = resource.description

    override val parameters: MutableList<RestParameter> = ArrayList()



    override val methods: List<SuuRestMethod>
        get() = resource.restMethodList.map {
            RestMethodDirect(it)
        }

    override val childResources: List<SuuRestResource>
        get() = resource.childResourceList.map { ResourceDirect(it) }

    override fun getChildResource(name: String): SuuRestResource? {
        return resource.childResourceList.filter { it.name == name }.map { ResourceDirect(it) }.first()
    }

    override fun getMethod(name: String): SuuRestMethod? {
        return resource.restMethodList.filter { it.name == name }.map { RestMethodDirect(it) }.first()
    }

    override fun createMethod(name: String): SuuRestMethod {
        val method = resource.addNewMethod(name)
        return RestMethodDirect(method)
    }

    override fun createChildResource(name: String, path: String): SuuRestResource {
        val childResource = resource.addNewChildResource(name, path)
        return ResourceDirect(childResource)
    }


}