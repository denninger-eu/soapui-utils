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


    override fun enter(restService: SuuRestService): VisitResult {
        referenceRestService = referenceProject.getRestService(restService.name!!)
        if (referenceRestService == null) {
            return VisitResult.TERMINATE
        }
        restService.description = referenceRestService!!.description
        restService.basePath = referenceRestService!!.basePath

        return VisitResult.CONTINUE
    }

    override fun exit(restService: SuuRestService) {
        val missingResources = ArrayList(referenceRestService!!.resources)
        restService.resources.forEach { found -> missingResources.removeIf { it.name == found.name } }

        val copyListener = CopyRestServiceListener(restService)
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

    override fun enterResource(suuResource: SuuRestResource): VisitResult {
        val reference = findReferenceResource(suuResource.name)
        if (reference == null) {
            TODO("implement, remove")
            return VisitResult.TERMINATE
        }
        suuResource.description = reference.description
        suuResource.path = reference.path

        misc.handleParameters(
            suuResource.parameters,
            reference.parameters
        )

        referenceResources.push(reference)
        return VisitResult.CONTINUE
    }

    override fun exitResource(suuResource: SuuRestResource) {
        val missingMethods = ArrayList(referenceResources.peek().methods)
        suuResource.methods.forEach { found -> missingMethods.removeIf { it.name == found.name } }

        val copyListener = CopyRestServiceListener(suuResource)
        for (missingMethod in missingMethods) {
            missingMethod.apply(copyListener)
        }

        val missingChildResources = ArrayList(referenceResources.peek().childResources)
        suuResource.childResources.forEach { found -> missingChildResources.removeIf { it.name == found.name } }
        for (missingChildResource in missingChildResources) {
            missingChildResource.apply(copyListener)
        }
        referenceResources.pop()
    }


    override fun enterMethod(suuRestMethod: SuuRestMethod): VisitResult {
        val reference = referenceResources.peek().getMethod(suuRestMethod.name)
            ?: return VisitResult.TERMINATE
        suuRestMethod.description = reference.description
        suuRestMethod.httpMethod = reference.httpMethod

        misc.handleParameters(
            suuRestMethod.parameters,
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


    override fun handleRequest(suuRestRequest: SuuRestRequest) {
        val referenceRequest = referenceMethod!!.getRequest(suuRestRequest.name)
        if (referenceRequest == null) {
            // Move to lost and found
            return
        }
        SyncRestRequest().handle(suuRestRequest, referenceRequest)
    }

}