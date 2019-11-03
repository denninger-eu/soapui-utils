package eu.k5.soapui.streams.direct.model

import com.eviware.soapui.impl.rest.RestMethod
import com.eviware.soapui.impl.rest.RestRequestInterface
import eu.k5.soapui.streams.jaxb.rest.RestParameter
import eu.k5.soapui.streams.model.rest.SuuRestMethod
import eu.k5.soapui.streams.model.rest.SuuRestRequest


class RestMethodDirect(
    private val restMethod: RestMethod
) : SuuRestMethod {


    override var name: String? = restMethod.name
    override var description: String? = restMethod.description
    override val parameters: MutableList<RestParameter> = ArrayList()


    override var method: SuuRestMethod.HttpMethod? =
        map(restMethod.method)

    override val requests: List<SuuRestRequest>
        get() = restMethod.requestList.map { RestRequestDirect(it) }


    override fun createRequest(name: String): SuuRestRequest {
        val request = restMethod.addNewRequest(name)
        return RestRequestDirect(request)
    }

    companion object {
        private fun map(method: RestRequestInterface.HttpMethod): SuuRestMethod.HttpMethod {
            return SuuRestMethod.HttpMethod.valueOf(method.name)
        }
    }
}
