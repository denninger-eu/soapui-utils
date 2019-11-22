package eu.k5.soapui.streams.listener.sync

import eu.k5.soapui.streams.apply
import eu.k5.soapui.streams.listener.VisitResult
import eu.k5.soapui.streams.model.rest.SuuRestServiceListener
import eu.k5.soapui.streams.model.SuProject
import eu.k5.soapui.streams.model.rest.SuuRestMethod
import eu.k5.soapui.streams.model.rest.SuuRestRequest
import eu.k5.soapui.streams.model.rest.SuuRestResource
import eu.k5.soapui.streams.model.rest.SuuRestService
import eu.k5.soapui.streams.Environment
import java.util.*
import kotlin.collections.ArrayList

class SyncRestServiceListener(
    private val env: Environment,
    private val referenceProject: SuProject,
    private val targetProject: SuProject
) : SuuRestServiceListener {


    private val misc = SyncMisc()

    private var referenceRestService: SuuRestService? = null
    private var referenceResources: Deque<SuuRestResource> =
        ArrayDeque()
    private var referenceMethod: SuuRestMethod? = null


    override fun enter(targetRestService: SuuRestService): VisitResult {
        referenceRestService = referenceProject.getRestService(targetRestService.name!!)
        if (referenceRestService == null) {
            return VisitResult.TERMINATE
        }
        targetRestService.description = referenceRestService!!.description
        targetRestService.basePath = referenceRestService!!.basePath

        return VisitResult.CONTINUE
    }

    override fun exit(targetRestService: SuuRestService) {
        val missingResources = ArrayList(referenceRestService!!.resources)
        targetRestService.resources.forEach { found -> missingResources.removeIf { it.name == found.name } }

        val copyListener = CopyRestServiceListener(targetRestService)
        for (missingResource in missingResources) {
            missingResource.apply(copyListener)
        }

    }


    private fun findReferenceResource(name: String): SuuRestResource? {
        return if (!referenceResources.isEmpty()) {
            referenceResources.peek().getChildResource(name)
        } else {
            referenceRestService!!.getResource(name)
        }
    }

    override fun enterResource(targetRestResource: SuuRestResource): VisitResult {
        val reference = findReferenceResource(targetRestResource.name!!)
        if (reference == null) {
            TODO("implement, remove")
            return VisitResult.TERMINATE
        }
        targetRestResource.description = reference.description
        targetRestResource.path = reference.path

        misc.handleParameters(
            targetRestResource.parameters,
            reference.parameters
        )

        referenceResources.push(reference)
        return VisitResult.CONTINUE
    }

    override fun exitResource(targetRestResource: SuuRestResource) {
        val missingMethods = ArrayList(referenceResources.peek().methods)
        targetRestResource.methods.forEach { found -> missingMethods.removeIf { it.name == found.name } }

        val copyListener = CopyRestServiceListener(targetRestResource)
        for (missingMethod in missingMethods) {
            missingMethod.apply(copyListener)
        }

        val missingChildResources = ArrayList(referenceResources.peek().childResources)
        targetRestResource.childResources.forEach { found -> missingChildResources.removeIf { it.name == found.name } }
        for (missingChildResource in missingChildResources) {
            missingChildResource.apply(copyListener)
        }
        referenceResources.pop()
    }


    override fun enterMethod(targetRestMethod: SuuRestMethod): VisitResult {
        val reference = referenceResources.peek().getMethod(targetRestMethod.name)
            ?: return VisitResult.TERMINATE
        targetRestMethod.description = reference.description
        targetRestMethod.httpMethod = reference.httpMethod

        misc.handleParameters(
            targetRestMethod.parameters,
            reference.parameters
        )
        referenceMethod = reference
        return VisitResult.CONTINUE
    }

    override fun exitMethod(suuRestMethod: SuuRestMethod) {

        val missingRequests = ArrayList(referenceMethod!!.requests)
        suuRestMethod.requests.forEach { found -> missingRequests.removeIf { it.name == found.name } }
        val copyListener = CopyRestServiceListener(suuRestMethod)

        for (missingRequest in missingRequests) {
            copyListener.handleRequest(missingRequest)
        }
    }


    override fun handleRequest(targetRestRequest: SuuRestRequest) {
        val referenceRequest = referenceMethod!!.getRequest(targetRestRequest.name)
        if (referenceRequest == null) {
            // Move to lost and found
            return
        }
        SyncRestRequest().handle(targetRestRequest, referenceRequest)
    }

}