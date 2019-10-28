package eu.k5.soapui.streams.direct

import com.eviware.soapui.impl.rest.RestRequest
import eu.k5.soapui.streams.listener.resource.SuuRestServiceListener
import eu.k5.soapui.streams.model.Project
import eu.k5.soapui.streams.model.SuProject
import eu.k5.soapui.streams.model.rest.*
import eu.k5.soapui.streams.model.rest.SuuResource
import eu.k5.soapui.visitor.listener.*
import org.slf4j.LoggerFactory
import java.util.*

class DirectBindListener : SuListener {

    var project: Project? = null

    override fun enterProject(env: Environment, suProject: SuProject) {
        project = Project(name = suProject.name)
    }

    override fun exitProject() {
    }


    override fun createWsdlInterfaceListener(): SuWsdlInterfaceListener {
        return SuWsdlInterfaceListener.NO_OP
    }

    override fun createResourceListener(): SuuRestServiceListener {
        return DirectBindRestServiceListener(project!!)
    }


    override fun createTestSuiteListener(): SuTestSuiteListener? {
        return null
    }
}

class DirectBindRestServiceListener(
    private val project: SuProject
) : SuuRestServiceListener {


    private var restService: RestService? = null

    private val resources: Deque<Resource?> = ArrayDeque()
    private var currentResource: Resource? = null

    private var currentMethod: RestMethod? = null

    override fun enter(env: Environment, suuRestService: SuuRestService) {
        restService = RestService(suuRestService.name, suuRestService.description)

    }

    override fun exit() {
        project.addRestService(restService!!)
    }


    override fun enterResource(env: Environment, suuResource: SuuResource) {
        LOGGER.info("enterResource {} {}", suuResource.path, suuResource.name)
        val resource = Resource(suuResource.name, suuResource.path)

        resource.parameters.addAll(suuResource.parameters.map { it.copy() })

        if (currentResource != null) {
            resources.push(currentResource)
        }
        currentResource = resource
    }

    override fun exitResource() {
        val resource = currentResource!!
        if (resources.isEmpty()) {
            restService!!.addResource(resource)
            currentResource = null
        } else {
            currentResource = resources.pop()
            currentResource!!.addResource(resource)
        }
    }


    override fun enterMethod(env: Environment, suuRestMethod: SuuRestMethod) {
        val method = RestMethod(suuRestMethod.name, suuRestMethod.description, suuRestMethod.method)
        method.parameters.addAll(suuRestMethod.parameters.map { it.copy() })
        currentMethod = method
    }

    override fun exitMethod() {
        currentResource?.addMethod(currentMethod!!)
    }


    override fun handleRequest(env: Environment, suuRestRequest: SuuRestRequest) {
        val request = RestRequest(suuRestRequest.name, suuRestRequest.description)
        request.parameters.addAll(suuRestRequest.parameters.map { it.copy() })
        currentMethod?.addRequest(request)
    }


    companion object {
        private val LOGGER = LoggerFactory.getLogger(DirectBindRestServiceListener::class.java)
    }
}