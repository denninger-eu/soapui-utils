package eu.k5.soapui.streams.direct.model.rest

import com.eviware.soapui.impl.rest.RestService
import eu.k5.soapui.streams.model.rest.SuuRestResource
import eu.k5.soapui.streams.model.rest.SuuRestService
import java.util.*

class RestServiceDirect(
    private val restService: RestService
) : SuuRestService {


    override var name: String
        get() = restService.name
        set(value) {
            restService.name = value
        }

    override var description: String?
        get() = restService.description
        set(value) {
            restService.description = value
        }

    override var basePath: String
        get() = restService.basePath ?: ""
        set(value) {
            restService.basePath = value
        }


    override val endpoints: List<String>
        get() = restService.endpoints.toList()


    override fun addEndpoint(endpoint: String) {
        restService.addEndpoint(endpoint)
    }

    override fun removeEndpoint(endpoint: String) {
        restService.removeEndpoint(endpoint)
    }


    override fun createResource(name: String, path: String): SuuRestResource {
        val newResource = restService.addNewResource(name, path)
        return ResourceDirect(newResource)
    }

    override val resources: List<SuuRestResource>
        get() = restService.resourceList.map { ResourceDirect(it) }

}
