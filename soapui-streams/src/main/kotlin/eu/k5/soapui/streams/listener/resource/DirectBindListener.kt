/*
package eu.k5.soapui.streams.listener.resource

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

    override fun exitProject(suuProject: SuProject) {
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
    val project: Project
) : SuuRestServiceListener {


    private var restService: RestService? = null

    private val restResources: Deque<RestResource> = ArrayDeque()

    private var currentMethod: RestMethod? = null

    override fun enter(suuRestService: SuuRestService) {
        restService = project.createRestService(suuRestService.name!!)
        restService!!.description = suuRestService.description
        restService!!.basePath = suuRestService.basePath
    }

    override fun exit(suuRestService: SuuRestService) {
    }


    override fun enterResource(suuResource: SuuResource) {
        LOGGER.info("enterResource {} {}", suuResource.path, suuResource.name)

        val resource =
            if (!restResources.isEmpty()) {
                restResources.peek().createChildResource(suuResource.name!!, suuResource.path!!)
            } else {
                restService!!.createResource(suuResource.name!!, suuResource.path!!)
            }

        resource.parameters.addAll(suuResource.parameters.map { it.copy() })
        resource.description = suuResource.description
        restResources.push(resource)
    }

    override fun exitResource(suuResource: SuuResource) {
    }

    override fun enterMethod(suuRestMethod: SuuRestMethod) {
        val method = restResources.peek().createMethod(suuRestMethod.name!!)
        method.description = suuRestMethod.description
        method.method = suuRestMethod.method
        method.parameters.addAll(suuRestMethod.parameters.map { it.copy() })
        currentMethod = method
    }

    override fun exitMethod(suuRestMethod: SuuRestMethod) {
    }


    override fun handleRequest(suuRestRequest: SuuRestRequest) {
        val request = currentMethod!!.createRequest(suuRestRequest.name!!)
        request.description = suuRestRequest.description
        request.parameters.addAll(suuRestRequest.parameters.map { it.copy() })
    }


    companion object {
        private val LOGGER = LoggerFactory.getLogger(DirectBindRestServiceListener::class.java)
    }
}*/
