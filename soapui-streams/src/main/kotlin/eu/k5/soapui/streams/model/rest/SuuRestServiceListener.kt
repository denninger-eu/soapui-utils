package eu.k5.soapui.streams.model.rest

import eu.k5.soapui.streams.listener.VisitResult

interface SuuRestServiceListener {

    fun enter(restService: SuuRestService): VisitResult
    fun exit(restService: SuuRestService)

    fun enterResource(suuResource: SuuRestResource): VisitResult
    fun exitResource(suuResource: SuuRestResource)

    fun enterMethod(suuRestMethod: SuuRestMethod): VisitResult
    fun exitMethod(suuRestMethod: SuuRestMethod)

    fun handleRequest(suuRestRequest: SuuRestRequest)

    companion object {
        val NO_OP = object : SuuRestServiceListener {
            override fun handleRequest(suuRestRequest: SuuRestRequest) {
            }

            override fun enterMethod(directMethod: SuuRestMethod) : VisitResult {
                return VisitResult.TERMINATE
            }

            override fun exitMethod(suuRestMethod: SuuRestMethod) {
            }

            override fun exit(restService: SuuRestService) {
            }

            override fun exitResource(suuResource: SuuRestResource) {
            }

            override fun enterResource(suuResource: SuuRestResource): VisitResult {
                return VisitResult.TERMINATE
            }

            override fun enter(resource: SuuRestService): VisitResult {
                return VisitResult.TERMINATE
            }

        }
    }
}
