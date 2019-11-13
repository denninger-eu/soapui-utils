package eu.k5.soapui.streams.direct.model.rest

import com.eviware.soapui.impl.rest.RestRequestInterface
import com.eviware.soapui.impl.rest.RestResource
import eu.k5.soapui.streams.model.rest.SuuRestResource
import eu.k5.soapui.streams.model.rest.SuuRestMethod
import eu.k5.soapui.streams.model.rest.SuuRestParameters

class ResourceDirect(
    private val resource: RestResource
) : SuuRestResource {


    override var name: String
        get() = resource.name ?: ""
        set(value) {
            resource.name = value
        }
    override var path: String?
        get() = resource.path
        set(value) {
            resource.path = value
        }
    override var description: String?
        get() = resource.description
        set(value) {
            resource.description = value
        }

    override val parameters: SuuRestParameters =
        RestParametersDirect(
            resource.params,
            RestParametersDirect.Owner.RESOURCE
        )


    override val methods: List<SuuRestMethod>
        get() = resource.restMethodList.map {
            RestMethodDirect(it)
        }

    override val childResources: List<SuuRestResource>
        get() = resource.childResourceList.map { ResourceDirect(it) }

    override fun getChildResource(name: String): SuuRestResource? {
        return resource.childResourceList.filter { it.name == name }.map {
            ResourceDirect(
                it
            )
        }.firstOrNull()
    }

    override fun getMethod(name: String): SuuRestMethod? {
        return resource.restMethodList.filter { it.name == name }.map {
            RestMethodDirect(
                it
            )
        }.firstOrNull()
    }

    override fun createMethod(name: String): SuuRestMethod {
        val method = resource.addNewMethod(name)
        method.method = RestRequestInterface.HttpMethod.GET
        return RestMethodDirect(method)
    }

    override fun createChildResource(name: String, path: String): SuuRestResource {
        val childResource = resource.addNewChildResource(name, path)
        return ResourceDirect(childResource)
    }


}