package eu.k5.soapui.streams.listener.resource

import eu.k5.soapui.streams.model.rest.SuuResource
import eu.k5.soapui.streams.model.rest.SuuRestMethod
import eu.k5.soapui.streams.model.rest.SuuRestRequest
import eu.k5.soapui.streams.model.rest.SuuRestService
import eu.k5.soapui.visitor.listener.Environment

interface SuuRestServiceListener {

    fun enter(env: Environment, restService: SuuRestService)
    fun exit()

    fun enterResource(env: Environment, resource: SuuResource)
    fun exitResource()

    fun enterMethod(env: Environment, suuRestMethod: SuuRestMethod)
    fun exitMethod()

    fun handleRequest(env: Environment, suuRestRequest: SuuRestRequest)

    companion object {
        val NO_OP = object : SuuRestServiceListener {
            override fun handleRequest(env: Environment, suuRestRequest: SuuRestRequest) {
            }

            override fun enterMethod(env: Environment, directMethod: SuuRestMethod) {
            }

            override fun exitMethod() {
            }

            override fun exit() {
            }

            override fun exitResource() {
            }

            override fun enterResource(env: Environment, resource: SuuResource) {
            }

            override fun enter(env: Environment, resource: SuuRestService) {
            }

        }
    }
}
