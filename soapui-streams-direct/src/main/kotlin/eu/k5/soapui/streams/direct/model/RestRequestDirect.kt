package eu.k5.soapui.streams.direct.model

import com.eviware.soapui.impl.rest.RestRequest
import eu.k5.soapui.streams.jaxb.rest.RestParameter
import eu.k5.soapui.streams.model.rest.SuuRestRequest

class RestRequestDirect(
    private val request: RestRequest

) : SuuRestRequest {
    override var name: String? = request.name
    override var description: String? = request.description


    override val parameters: MutableList<RestParameter> = ArrayList()

}