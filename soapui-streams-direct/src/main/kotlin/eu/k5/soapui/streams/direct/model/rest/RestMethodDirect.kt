package eu.k5.soapui.streams.direct.model.rest

import com.eviware.soapui.impl.rest.RestMethod
import com.eviware.soapui.impl.rest.RestRequestInterface
import eu.k5.soapui.streams.model.rest.SuuRestMethod
import eu.k5.soapui.streams.model.rest.SuuRestParameters
import eu.k5.soapui.streams.model.rest.SuuRestRequest


class RestMethodDirect(
    private val restMethod: RestMethod
) : SuuRestMethod {


    override val parameters: SuuRestParameters =
        RestParametersDirect(
            restMethod.params,
            RestParametersDirect.Owner.METHOD
        )

    override var name: String
        get() = restMethod.name ?: ""
        set(value) {
            restMethod.name = value
        }
    override var description: String?
        get() = restMethod.description
        set(value) {
            restMethod.description = value
        }


    override var httpMethod: SuuRestMethod.HttpMethod?
        get() = map(restMethod.method)
        set(value) {
            restMethod.method = map(value)
        }

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

        private fun map(method: SuuRestMethod.HttpMethod?): RestRequestInterface.HttpMethod {
            if (method == null){
                return RestRequestInterface.HttpMethod.GET
            }
            return RestRequestInterface.HttpMethod.valueOf(method.name)
        }
    }
}
