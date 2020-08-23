package eu.k5.soapui.streams.listener.difference

import eu.k5.soapui.streams.listener.VisitResult
import eu.k5.soapui.streams.model.rest.SuuRestServiceListener
import eu.k5.soapui.streams.model.SuProject
import eu.k5.soapui.streams.model.rest.*
import java.util.*

class DifferenceRestServiceListener(
    private val project: SuProject,
    private val differences: Differences

) : SuuRestServiceListener {

    private val misc = DifferenceMisc(differences)

    private var referenceRestService: SuuRestService? = null

    private val referenceResources = ArrayDeque<SuuRestResource>()

    private var referenceMethod: SuuRestMethod? = null

    override fun enter(restService: SuuRestService): VisitResult {
        differences.pushRestService("restService")
        val refRestService = project.getRestService(restService.name)
        if (refRestService == null) {
            differences.addAdditional(restService.name)
            return VisitResult.TERMINATE
        }

        differences.addChange("name", refRestService.name, restService.name)
        differences.addChange("description", refRestService.description, restService.description)
        differences.addChange("basePath", refRestService.basePath, restService.basePath)

        handleEndpoints(restService, refRestService)

        referenceRestService = refRestService
        return VisitResult.CONTINUE
    }

    private fun handleEndpoints(actual: SuuRestService, reference: SuuRestService) {
        for (endpoint in actual.endpoints) {
            if (!reference.endpoints.contains(endpoint)) {
                differences.addAdditional(endpoint)
            }
        }
        val missing = ArrayList(reference.endpoints)
        missing.removeAll(actual.endpoints)
        for (missingEndpoint in missing) {
            differences.addMissing(missingEndpoint)
        }

    }


    override fun exit(restService: SuuRestService) {
        // Search missing
        differences.pop()
    }

    override fun enterResource(suuResource: SuuRestResource): VisitResult {
        differences.pushResource("resource")
        val refResource = getResource(suuResource.name)
        if (refResource == null) {
            differences.addAdditional(suuResource.name)
            return VisitResult.TERMINATE
        }

        differences.addChange("description", refResource.description, suuResource.description)
        misc.handleParameters(refResource.parameters, suuResource.parameters)
        referenceResources.push(refResource)
        return VisitResult.CONTINUE
    }

    private fun getResource(name: String): SuuRestResource? {
        return if (referenceResources.isEmpty()) {
            referenceRestService!!.getResource(name)
        } else {
            referenceResources.peek().getChildResource(name)
        }
    }

    override fun exitResource(suuResource: SuuRestResource) {
        referenceResources.pop()
        differences.pop()
    }

    override fun enterMethod(suuRestMethod: SuuRestMethod): VisitResult {
        val refMethod = referenceResources.peek().getMethod(suuRestMethod.name)
        if (refMethod == null) {
            differences.addAdditional(suuRestMethod.name)
            return VisitResult.TERMINATE
        }
        differences.pushMethod(suuRestMethod.name)
        differences.addChange("name", refMethod.name, suuRestMethod.name)
        differences.addChange("description", refMethod.description, suuRestMethod.description)
        differences.addChange("httpMethod", refMethod.httpMethod, suuRestMethod.httpMethod)

        misc.handleParameters(refMethod.parameters, suuRestMethod.parameters)
        this.referenceMethod = refMethod
        return VisitResult.CONTINUE
    }

    override fun exitMethod(suuRestMethod: SuuRestMethod) {
        differences.pop()
    }

    override fun handleRequest(suuRestRequest: SuuRestRequest) {
        DifferenceRestRequest(differences).handle(referenceMethod!!.getRequest(suuRestRequest.name), suuRestRequest)
    }


}