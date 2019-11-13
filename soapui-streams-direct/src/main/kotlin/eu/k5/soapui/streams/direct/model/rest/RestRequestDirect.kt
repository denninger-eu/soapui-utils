package eu.k5.soapui.streams.direct.model.rest

import com.eviware.soapui.impl.rest.RestRequest
import eu.k5.soapui.streams.model.rest.SuuRestParameters
import eu.k5.soapui.streams.model.rest.SuuRestRequest

class RestRequestDirect(
    private val request: RestRequest

) : SuuRestRequest {

    override var name: String
        get() = request.name ?: ""
        set(value) {
            request.name = value
        }
    override var description: String?
        get() = request.description
        set(value) {
            request.description = value
        }

    override var content: String?
        get() = request.requestContent
        set(value) {
            request.requestContent = value
        }


    override val parameters: SuuRestParameters =
        RestParametersDirect(
            request.params,
            RestParametersDirect.Owner.REQUEST
        )

}