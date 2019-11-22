package eu.k5.soapui.streams.listener.sync

import eu.k5.soapui.streams.listener.VisitResult
import eu.k5.soapui.streams.model.rest.SuuRestServiceListener
import eu.k5.soapui.streams.model.SuProject
import eu.k5.soapui.streams.model.rest.*
import java.util.*
import kotlin.collections.ArrayList

class CopyRestServiceListener(
    private val target: SuProject?
) : SuuRestServiceListener {

    private val misc = SyncMisc()

    constructor(restService: SuuRestService) : this(null) {
        targetRestService = restService
    }

    constructor(suuRestResource: SuuRestResource) : this(null) {
        targetResources.push(suuRestResource)
    }

    constructor(suuRestMethod: SuuRestMethod) : this(null) {
        targetMethod = suuRestMethod
    }

    private var targetRestService: SuuRestService? = null

    private val targetResources: Deque<SuuRestResource> =
        ArrayDeque()

    private var targetMethod: SuuRestMethod? = null

    override fun enter(restService: SuuRestService): VisitResult {
        val newRestService = target!!.createRestService(restService.name!!)
        newRestService.description = restService.description
        newRestService.basePath = restService.basePath
        targetRestService = newRestService

        return VisitResult.CONTINUE
    }

    override fun exit(restService: SuuRestService) {

    }

    override fun enterResource(suuResource: SuuRestResource): VisitResult {
        val newResource = if (targetResources.isEmpty()) {
            targetRestService!!.createResource(suuResource.name!!, suuResource.path!!)
        } else {
            targetResources.peek().createChildResource(suuResource.name!!, suuResource.path!!)
        }
        newResource.description = suuResource.description
        misc.handleParameters(
            newResource.parameters,
            suuResource.parameters
        )
        targetResources.push(newResource)
        return VisitResult.CONTINUE
    }

    override fun exitResource(suuResource: SuuRestResource) {
        targetResources.pop()
    }

    override fun enterMethod(suuRestMethod: SuuRestMethod): VisitResult {
        val newMethod = targetResources.peek().createMethod(suuRestMethod.name!!)
        newMethod.description = suuRestMethod.description
        newMethod.httpMethod = suuRestMethod.httpMethod
        misc.handleParameters(
            newMethod.parameters,
            suuRestMethod.parameters
        )
        targetMethod = newMethod
        return VisitResult.CONTINUE
    }

    override fun exitMethod(suuRestMethod: SuuRestMethod) {
    }

    override fun handleRequest(suuRestRequest: SuuRestRequest) {
        val newRequest = targetMethod!!.createRequest(suuRestRequest.name!!)
        newRequest.description = suuRestRequest.description
        newRequest.content = suuRestRequest.content
        misc.copyHeaders(suuRestRequest, newRequest)
        misc.handleParameters(
            newRequest.parameters!!,
            suuRestRequest.parameters!!
        )
    }

    companion object {

    }
}