package eu.k5.soapui.streams.listener.resource

import eu.k5.soapui.streams.apply
import eu.k5.soapui.streams.model.SuProject
import eu.k5.soapui.streams.model.rest.*
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

        val copyListener = CopyListener(suuProject).createResourceListener()
        for (missingRestService in missingRestServices) {
            missingRestService.apply(copyListener)
        }
    }

    override fun createWsdlInterfaceListener(): SuWsdlInterfaceListener? {
        return SuWsdlInterfaceListener.NO_OP
    }

    override fun createResourceListener(): SuuRestServiceListener {
        return DirectSyncResourceListener(
            environment!!,
            referenceProject,
            targetProject!!
        )
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
    private var referenceResources: Deque<SuuRestResource> = ArrayDeque()
    private var referenceMethod: SuuRestMethod? = null


    override fun enter(restService: SuuRestService) {
        referenceRestService = referenceProject.getRestService(restService.name!!)
        if (referenceRestService == null) {
//            TODO("implement, remove")
        }
    }

    override fun exit(suuRestService: SuuRestService) {
        val missingResources = ArrayList(referenceRestService!!.resources)
        suuRestService.resources.forEach { found -> missingResources.removeIf { it.name == found.name } }

        val copyListener = CopyRestServiceListener(suuRestService)
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

    override fun enterResource(suuResource: SuuRestResource) {
        val ref = findReferenceResource(suuResource.name!!)
        if (ref == null) {
            TODO("implement, remove")
        }
        referenceResources.push(ref)
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

    }


    override fun enterMethod(suuRestMethod: SuuRestMethod) {
        referenceMethod = referenceResources.peek().getMethod(suuRestMethod.name!!)


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
        val referenceRequest = referenceMethod!!.getRequest(suuRestRequest.name!!)
        if (referenceRequest == null) {
            return
            //TODO("implement remove")
        }

        suuRestRequest.description = referenceRequest.description
    }

}

class CopyListener(
    val suuProject: SuProject
) : SuListener {
    override fun enterProject(env: Environment, project: SuProject) {
    }

    override fun exitProject(suuProject: SuProject) {
    }

    override fun createWsdlInterfaceListener(): SuWsdlInterfaceListener? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun createResourceListener(): SuuRestServiceListener {
        return CopyRestServiceListener(suuProject)
    }

    override fun createTestSuiteListener(): SuTestSuiteListener? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}

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

    private val targetResources: Deque<SuuRestResource> = ArrayDeque()

    private var targetMethod: SuuRestMethod? = null

    override fun enter(restService: SuuRestService) {
        val newRestService = target!!.createRestService(restService.name!!)
        newRestService.description = restService.description
        newRestService.basePath = restService.basePath
        targetRestService = newRestService
    }

    override fun exit(restService: SuuRestService) {

    }

    override fun enterResource(suuResource: SuuRestResource) {
        val newResource = if (targetResources.isEmpty()) {
            targetRestService!!.createResource(suuResource.name!!, suuResource.path!!)
        } else {
            targetResources.peek().createChildResource(suuResource.name!!, suuResource.path!!)
        }
        println(suuResource.description)
        newResource.description = suuResource.description
        targetResources.push(newResource)
    }

    override fun exitResource(suuResource: SuuRestResource) {
        targetResources.pop()
    }

    override fun enterMethod(suuRestMethod: SuuRestMethod) {
        val newMethod = targetResources.peek().createMethod(suuRestMethod.name!!)
        newMethod.description = suuRestMethod.description
        newMethod.httpMethod = suuRestMethod.httpMethod
        targetMethod = newMethod
    }

    override fun exitMethod(suuRestMethod: SuuRestMethod) {


    }

    override fun handleRequest(suuRestRequest: SuuRestRequest) {
        val newRequest = targetMethod!!.createRequest(suuRestRequest.name!!)
        newRequest.description = suuRestRequest.description
        newRequest.content = suuRestRequest.content

        handleParameters(newRequest.parameters!!, suuRestRequest.parameters!!)
    }

    private fun handleParameters(target: SuuRestParameters, source: SuuRestParameters) {

        val missing = ArrayList<String>()
        for (targetParameter in target.parameters) {
            if (!source.hasParameter(targetParameter.name)) {
                missing.add(targetParameter.name!!)
            }
        }
        missing.forEach { target.remove(it) }

        for (parameter in source.parameters) {
            target.addOrUpdate(parameter)
        }

        target.addOrUpdate("testName", "testValue", "testStyle")
        target.addOrUpdate("testName", "testValue", "testStyle")

    }
}