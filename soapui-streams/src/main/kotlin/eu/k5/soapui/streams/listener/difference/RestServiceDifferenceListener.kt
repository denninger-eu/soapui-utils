package eu.k5.soapui.streams.listener.difference

import eu.k5.soapui.streams.listener.VisitResult
import eu.k5.soapui.streams.listener.resource.SuuRestServiceListener
import eu.k5.soapui.streams.model.SuProject
import eu.k5.soapui.streams.model.rest.*
import java.util.*
import kotlin.collections.ArrayList

class RestServiceDifferenceListener(
    private val project: SuProject,
    private val differences: Differences

) : SuuRestServiceListener {

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



        referenceRestService = refRestService
        return VisitResult.CONTINUE
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
        if (actualResource.description != refResource.description) {
            differences.addChange("description")
        }

        handleParameters(refResource.parameters, actualResource.parameters)
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

        handleParameters(refMethod.parameters, actualMethod.parameters)
        this.referenceMethod = refMethod
        return VisitResult.CONTINUE
    }

    override fun exitMethod(suuRestMethod: SuuRestMethod) {
        differences.pop()
    }

    override fun handleRequest(actualRequest: SuuRestRequest) {

        val refRequest = referenceMethod!!.getRequest(actualRequest.name)
        if (refRequest == null) {
            differences.addAdditional(actualRequest.name)
            return
        }
        differences.pushRequest(actualRequest.name)
        differences.addChange("description", refRequest.description, actualRequest.description)
        differences.addChange("content", refRequest.content?.trim(), actualRequest.content?.trim())
        handleParameters(refRequest.parameters, actualRequest.parameters)

        handleHeaders(refRequest.headers, actualRequest.headers)

        differences.pop()
    }

    private fun handleParameters(refParameters: SuuRestParameters, actual: SuuRestParameters) {
        for (referenceParameter in refParameters.parameters) {
            val actualParameter = actual.byName(referenceParameter.name)
            if (actualParameter != null) {
                differences.addChange("value", referenceParameter.value, actualParameter.value)
                differences.addChange("style", referenceParameter.style, actualParameter.style)
            } else {
                differences.addAdditional(referenceParameter.name)
            }
        }
    }

    private fun handleHeaders(
        ref: List<SuuRestRequest.Header>, actual: List<SuuRestRequest.Header>
    ) {

        val found = ArrayList<String>()
        for (refHeader in ref) {
            val actualHeader = actual.firstOrNull { it.key == refHeader.key }
            if (actualHeader != null) {
                differences.addChange("headerValue", refHeader.value, actualHeader.value)
                found.add(refHeader.key)
            } else {
                differences.addAdditional(refHeader.key)
            }
        }
        val missing = ArrayList(actual)
        missing.removeIf { found.contains(it.key) }

        for (missingHeader in missing) {
            differences.addMissing(missingHeader.key)
        }
    }

}