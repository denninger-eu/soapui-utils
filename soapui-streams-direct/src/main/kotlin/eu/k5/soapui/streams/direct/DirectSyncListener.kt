package eu.k5.soapui.streams.direct

import eu.k5.soapui.streams.apply
import eu.k5.soapui.streams.listener.resource.SuuRestServiceListener
import eu.k5.soapui.streams.model.SuProject
import eu.k5.soapui.streams.model.rest.SuuResource
import eu.k5.soapui.streams.model.rest.SuuRestMethod
import eu.k5.soapui.streams.model.rest.SuuRestRequest
import eu.k5.soapui.streams.model.rest.SuuRestService
import eu.k5.soapui.visitor.listener.Environment
import eu.k5.soapui.visitor.listener.SuListener
import eu.k5.soapui.visitor.listener.SuTestSuiteListener
import eu.k5.soapui.visitor.listener.SuWsdlInterfaceListener
import java.util.*
import kotlin.collections.ArrayList

class DirectSyncListener(
    private val referenceProject: SuProject
) : SuListener {
    private var environment: Environment? = null

    private var targetProject: SuProject? = null

    override fun enterProject(env: Environment, project: SuProject) {
        environment = env
        targetProject = project
    }

    override fun exitProject(suuProject: SuProject) {
        val missingRestServices = ArrayList(referenceProject.restServices)
        suuProject.restServices.forEach { found -> missingRestServices.removeIf { it.name == found.name } }

        for (missingRestService in missingRestServices) {
            val newRestService = suuProject.createRestService(missingRestService.name!!)
            missingRestService.apply(CopyRestServiceListener(newRestService))
            suuProject.addRestService(newRestService)
        }
    }

    override fun createWsdlInterfaceListener(): SuWsdlInterfaceListener? {
        return SuWsdlInterfaceListener.NO_OP
    }

    override fun createResourceListener(): SuuRestServiceListener {
        return DirectSyncResourceListener(environment!!, referenceProject, targetProject!!)
    }

    override fun createTestSuiteListener(): SuTestSuiteListener? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}

class DirectSyncResourceListener(
    private val env: Environment,
    private val referenceProject: SuProject,
    private val targetProject: SuProject
) : SuuRestServiceListener {

    private var referenceRestService: SuuRestService? = null


    private var foundResources: Deque<MutableList<SuuResource>> = ArrayDeque()
    private var referenceResources: Deque<SuuResource> = ArrayDeque()


    private var referenceMethod: SuuRestMethod? = null

    private val foundRequests: MutableList<SuuRestRequest> = ArrayList()

    private var targetResoures: Deque<SuuResource> = ArrayDeque()

    override fun enter(restService: SuuRestService) {
        referenceRestService = referenceProject.getRestService(restService.name!!)
        if (referenceRestService == null) {
            TODO("implement, remove")
        }
    }

    override fun exit(restService: SuuRestService) {
    }


    private fun findReferenceResource(name: String): SuuResource? {
        return if (!referenceResources.isEmpty()) {
            referenceResources.peek().getChildResource(name)
        } else {
            referenceRestService!!.getResource(name)
        }
    }

    override fun enterResource(suuResource: SuuResource) {
        val ref = findReferenceResource(suuResource.name!!)
        if (ref == null) {
            TODO("implement, remove")
        }
        referenceResources.push(ref)
        foundResources.push(ArrayList())
        foundResources.peek().add(ref)


    }

    override fun exitResource(suuResource: SuuResource) {
        val newResources = referenceResources.peek().resources.filter { !foundResources.peek().contains(it) }
    }


    override fun enterMethod(suuRestMethod: SuuRestMethod) {
        referenceMethod = referenceResources.peek().getMethod(suuRestMethod.name!!)
        foundRequests.clear()

    }

    override fun exitMethod(suuRestMethod: SuuRestMethod) {
        val newRequests = referenceMethod!!.requests.filter { !foundRequests.contains(it) }

        for (newRequest in newRequests) {
            val request = suuRestMethod.createRequest(newRequest.name!!)

        }
    }


    override fun handleRequest(suuRestRequest: SuuRestRequest) {
        val referenceRequest = referenceMethod!!.getRequest(suuRestRequest.name!!)
        if (referenceRequest == null) {
            return
            //TODO("implement remove")
        }
        foundRequests.add(referenceRequest)

        suuRestRequest.description = referenceRequest.description
    }

}

class CopyListener(
    val suuRestService: SuuRestService
) {

}

class CopyRestServiceListener(
    private val target: SuuRestService
) : SuuRestServiceListener {

    private val targetResources: Deque<SuuResource> = ArrayDeque()

    private var targetMethod: SuuRestMethod? = null

    override fun enter(restService: SuuRestService) {
        target.description = restService.description
        target.basePath = restService.basePath
    }

    override fun exit(restService: SuuRestService) {

    }

    override fun enterResource(suuResource: SuuResource) {
        val newResource = target.createResource(suuResource.name!!, suuResource.path!!)
        newResource.description = suuResource.description
        target.addResource(newResource)

        targetResources.push(newResource)
    }

    override fun exitResource(suuResource: SuuResource) {
        targetResources.pop()
    }

    override fun enterMethod(suuRestMethod: SuuRestMethod) {
        val newMethod = targetResources.peek().createMethod(suuRestMethod.name!!)
        newMethod.description = suuRestMethod.description
        newMethod.method = suuRestMethod.method


    }

    override fun exitMethod(suuRestMethod: SuuRestMethod) {
    }

    override fun handleRequest(suuRestRequest: SuuRestRequest) {
    }

}