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

    override fun enter(actualRestService: SuuRestService): VisitResult {
        differences.pushRestService("restService")
        val refRestService = project.getRestService(actualRestService.name!!)
        if (refRestService == null) {
            differences.addAdditional(actualRestService.name!!)
            return VisitResult.TERMINATE
        }

        differences.addChange("name", refRestService.name, actualRestService.name)
        differences.addChange("description", refRestService.description, actualRestService.description)
        differences.addChange("basePath", refRestService.basePath, actualRestService.basePath)

        handleEndpoints(actualRestService, refRestService)

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

    override fun enterResource(actualResource: SuuRestResource): VisitResult {
        differences.pushResource("resource")
        val refResource = getResource(actualResource.name)
        if (refResource == null) {
            differences.addAdditional(actualResource.name)
            return VisitResult.TERMINATE
        }

        differences.addChange("description", refResource.description, actualResource.description)
        misc.handleParameters(refResource.parameters, actualResource.parameters)
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

    override fun enterMethod(actualMethod: SuuRestMethod): VisitResult {
        val refMethod = referenceResources.peek().getMethod(actualMethod.name)
        if (refMethod == null) {
            differences.addAdditional(actualMethod.name)
            return VisitResult.TERMINATE
        }
        differences.pushMethod(actualMethod.name)
        differences.addChange("name", refMethod.name, actualMethod.name)
        differences.addChange("description", refMethod.description, actualMethod.description)
        differences.addChange("httpMethod", refMethod.httpMethod, actualMethod.httpMethod)

        misc.handleParameters(refMethod.parameters, actualMethod.parameters)
        this.referenceMethod = refMethod
        return VisitResult.CONTINUE
    }

    override fun exitMethod(suuRestMethod: SuuRestMethod) {
        differences.pop()
    }

    override fun handleRequest(actualRequest: SuuRestRequest) {
        DifferenceRestRequest(differences).handle(referenceMethod!!.getRequest(actualRequest.name), actualRequest)
    }


}