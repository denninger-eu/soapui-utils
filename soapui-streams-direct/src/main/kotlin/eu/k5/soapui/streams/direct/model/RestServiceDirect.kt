package eu.k5.soapui.streams.direct.model

import com.eviware.soapui.impl.rest.RestService
import eu.k5.soapui.streams.model.rest.SuuResource
import eu.k5.soapui.streams.model.rest.SuuRestService
import java.lang.UnsupportedOperationException

class RestServiceDirect(
    private val restService: RestService
) : SuuRestService {
    override var name: String? = restService.name

    override var description: String? = restService.description

    override fun addResource(resource: SuuResource) = throw UnsupportedOperationException()

}
