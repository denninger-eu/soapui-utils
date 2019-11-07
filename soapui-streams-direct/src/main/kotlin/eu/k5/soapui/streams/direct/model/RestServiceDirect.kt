package eu.k5.soapui.streams.direct.model

import com.eviware.soapui.impl.rest.RestService
import eu.k5.soapui.streams.model.rest.SuuRestResource
import eu.k5.soapui.streams.model.rest.SuuRestService

class RestServiceDirect(
    private val restService: RestService
) : SuuRestService {


    override var name: String? = restService.name

    override var description: String? = restService.description

    override var basePath: String? = restService.basePath


    override fun createResource(name: String, path: String): SuuRestResource {
        val newResource = restService.addNewResource(name, path)
        return ResourceDirect(newResource)
    }

    override val resources: List<SuuRestResource>
        get() = restService.resourceList.map { ResourceDirect(it) }

}
