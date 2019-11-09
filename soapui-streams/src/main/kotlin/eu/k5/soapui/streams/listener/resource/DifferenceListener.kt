package eu.k5.soapui.streams.listener.resource

import eu.k5.soapui.streams.listener.VisitResult
import eu.k5.soapui.streams.model.SuProject
import eu.k5.soapui.streams.model.rest.*
import eu.k5.soapui.visitor.listener.Environment
import eu.k5.soapui.visitor.listener.SuListener
import eu.k5.soapui.visitor.listener.SuTestSuiteListener
import eu.k5.soapui.visitor.listener.SuWsdlInterfaceListener
import java.util.*
import kotlin.collections.ArrayList

class DifferenceListener(
    private val referenceProject: SuProject

) : SuListener {
    val differences = Differences()

    override fun enterProject(env: Environment, project: SuProject) {
        differences.pushProject("project")
        if (project.name != referenceProject.name) {
            differences.addChange("name")
        }
        if (project.description != referenceProject.description) {
            differences.addChange("description")
        }
    }

    override fun exitProject(suuProject: SuProject) {

    }

    override fun createWsdlInterfaceListener(): SuWsdlInterfaceListener? {
        return SuWsdlInterfaceListener.NO_OP
    }

    override fun createResourceListener(): SuuRestServiceListener {
        return RestServiceDifferenceListener(referenceProject, differences)
    }

    override fun createTestSuiteListener(): SuTestSuiteListener? {
        return SuTestSuiteListener.NO_OP
    }


}

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
        differences.addChange("content", refRequest.content, actualRequest.content)
        handleParameters(refRequest.parameters, actualRequest.parameters)
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

}

class Differences {
    private val differences = ArrayList<Difference>()
    private val path = ArrayDeque<Location>()

    fun addChange(path: String) {
        differences.add(DifferenceChange(ArrayList(this.path).map { it.toString() }.asReversed(), path))
    }

    fun addAdditional(name: String) {
        differences.add(DifferenceAdditional(ArrayList(this.path).map { it.toString() }.asReversed(), name))
    }

    fun pushProject(name: String) = this.path.push(Location(Type.PROJECT, name))
    fun pushRestService(name: String) = this.path.push(Location(Type.REST_SERVICE, name))
    fun pushResource(name: String) = this.path.push(Location(Type.RESOURCE, name))
    fun pushMethod(name: String) = this.path.push(Location(Type.METHOD, name))
    fun pushRequest(name: String) = this.path.push(Location(Type.REQUEST, name))

    fun push(type: Type, name: String) = this.path.push(Location(type, name))

    fun isEmpty(): Boolean = differences.isEmpty()

    fun pop() = path.pop()

    override fun toString(): String = differences.toString()
    fun <T> addChange(name: String, reference: T, actual: T) {
        if (reference != actual) {
            addChange(name)
        }
    }


    enum class Type {
        PROJECT, REST_SERVICE, RESOURCE, METHOD, REQUEST
    }

    class Location(
        val type: Type,
        val name: String
    ) {
        override fun toString(): String {
            return "${type.name}:${name}"
        }
    }

}


class DifferenceChange(
    val basePath: List<String>,
    val element: String
) : Difference {

    override fun toString(): String {
        return "Change $basePath.$element"
    }
}

class DifferenceAdditional(
    private val basePath: List<String>,
    private val element: String
) : Difference {

    override fun toString(): String {
        return "Additional $basePath.$element"
    }
}

interface Difference {


    enum class Type {
        CHANGE, INSERT, DELETE
    }
}