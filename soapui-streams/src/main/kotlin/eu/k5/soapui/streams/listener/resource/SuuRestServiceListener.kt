package eu.k5.soapui.streams.listener.resource

import eu.k5.soapui.streams.model.rest.SuuResource
import eu.k5.soapui.streams.model.rest.SuuRestMethod
import eu.k5.soapui.streams.model.rest.SuuRestRequest
import eu.k5.soapui.streams.model.rest.SuuRestService

interface SuuRestServiceListener {

    fun enter(restService: SuuRestService)
    fun exit(restService: SuuRestService)

    fun enterResource(suuResource: SuuResource)
    fun exitResource(suuResource: SuuResource)

    fun enterMethod(suuRestMethod: SuuRestMethod)
    fun exitMethod(suuRestMethod: SuuRestMethod)

    fun handleRequest(suuRestRequest: SuuRestRequest)

    companion object {
        val NO_OP = object : SuuRestServiceListener {
            override fun handleRequest(suuRestRequest: SuuRestRequest) {
            }

            override fun enterMethod(directMethod: SuuRestMethod) {
            }

            override fun exitMethod(suuRestMethod: SuuRestMethod) {
            }

            override fun exit(restService: SuuRestService) {
            }

            override fun exitResource(suuResource: SuuResource) {
            }

            override fun enterResource(suuResource: SuuResource) {
            }

            override fun enter(resource: SuuRestService) {
            }

        }
    }
}
