package eu.k5.soapui.streams.listener.copy

import eu.k5.soapui.streams.listener.VisitResult
import eu.k5.soapui.streams.listener.resource.SuuRestServiceListener
import eu.k5.soapui.streams.model.SuProject
import eu.k5.soapui.streams.model.rest.*
import java.util.*
import kotlin.collections.ArrayList

class CopyRestServiceListener(
    private val target: SuProject?
) : SuuRestServiceListener {

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
        println(suuResource.description)
        newResource.description = suuResource.description
        handleParameters(newResource.parameters, suuResource.parameters)
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
        handleParameters(newMethod.parameters, suuRestMethod.parameters)
        targetMethod = newMethod
        return VisitResult.CONTINUE
    }

    override fun exitMethod(suuRestMethod: SuuRestMethod) {
    }

    override fun handleRequest(suuRestRequest: SuuRestRequest) {
        val newRequest = targetMethod!!.createRequest(suuRestRequest.name!!)
        newRequest.description = suuRestRequest.description
        newRequest.content = suuRestRequest.content
        for (header in suuRestRequest.headers) {
            newRequest.addOrUpdateHeader(header)
        }
        handleParameters(newRequest.parameters!!, suuRestRequest.parameters!!)
    }

    companion object {
        fun handleParameters(target: SuuRestParameters, source: SuuRestParameters) {

            val missing = ArrayList<String>()
            for (targetParameter in target.allParameters) {
                if (!source.hasParameter(targetParameter.name)) {
                    missing.add(targetParameter.name!!)
                }
            }
            missing.forEach { target.remove(it) }

            for (parameter in source.allParameters) {
                target.addOrUpdate(parameter)
            }
        }
    }
}