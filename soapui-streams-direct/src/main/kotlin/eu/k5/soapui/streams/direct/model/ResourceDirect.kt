package eu.k5.soapui.streams.direct.model

import com.eviware.soapui.impl.rest.RestResource
import eu.k5.soapui.streams.model.rest.RestParameter
import eu.k5.soapui.streams.model.rest.SuuResource
import eu.k5.soapui.streams.model.rest.SuuRestMethod
import java.lang.UnsupportedOperationException

class ResourceDirect(
    val resource: RestResource
) : SuuResource {

    override var name: String? = resource.name
    override var path: String? = resource.path

    override val parameters: MutableList<RestParameter> = ArrayList()

    override fun addResource(resource: SuuResource) = throw UnsupportedOperationException()

    override fun addMethod(currentMethod: SuuRestMethod) = throw UnsupportedOperationException()

}