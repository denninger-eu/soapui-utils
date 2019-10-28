package eu.k5.soapui.streams.direct.model

import com.eviware.soapui.impl.rest.RestMethod
import com.eviware.soapui.impl.rest.RestRequestInterface
import eu.k5.soapui.streams.model.rest.RestParameter
import eu.k5.soapui.streams.model.rest.SuuRestMethod
import eu.k5.soapui.streams.model.rest.SuuRestRequest
import java.lang.UnsupportedOperationException


class RestMethodDirect(
    private val restMethod: RestMethod
) : SuuRestMethod {


    override var name: String? = restMethod.name
    override var description: String? = restMethod.description
    override val parameters: MutableList<RestParameter> = ArrayList()


    override fun addRequest(request: SuuRestRequest) = throw UnsupportedOperationException()

    override var method: SuuRestMethod.HttpMethod? =
        map(restMethod.method)

    companion object {
        private fun map(method: RestRequestInterface.HttpMethod): SuuRestMethod.HttpMethod {
            return SuuRestMethod.HttpMethod.valueOf(method.name)
        }
    }
}
