package eu.k5.soapui.streams.direct.model

import com.eviware.soapui.impl.rest.RestResource
import eu.k5.soapui.streams.model.rest.RestParameter
import eu.k5.soapui.streams.model.rest.SuuResource
import eu.k5.soapui.streams.model.rest.SuuRestMethod
import java.lang.UnsupportedOperationException

class ResourceDirect(
    private val resource: RestResource
) : SuuResource {


    override var name: String? = resource.name
    override var path: String? = resource.path
    override var description: String? = resource.description

    override val parameters: MutableList<RestParameter> = ArrayList()

    override fun addResource(resource: SuuResource) = throw UnsupportedOperationException()

    override fun addMethod(currentMethod: SuuRestMethod) = throw UnsupportedOperationException()


    override val methods: List<SuuRestMethod>
        get() = resource.restMethodList.map {
            RestMethodDirect(it)
        }

    override val resources: List<SuuResource>
        get() = resource.childResourceList.map { ResourceDirect(it) }

    override fun getChildResource(name: String): SuuResource? {
        return resource.childResourceList.filter { it.name == name }.map { ResourceDirect(it) }.first()
    }

    override fun getMethod(name: String): SuuRestMethod? {
        return resource.restMethodList.filter { it.name == name }.map { RestMethodDirect(it) }.first()
    }

    override fun createMethod(name: String): SuuRestMethod {
        val method = resource.addNewMethod(name)
        return RestMethodDirect(method)
    }

}