package eu.k5.soapui.streams.listener.resource

import eu.k5.soapui.streams.model.SuProject
import eu.k5.soapui.streams.model.rest.SuuRestResource
import eu.k5.soapui.streams.model.rest.SuuRestMethod
import eu.k5.soapui.streams.model.rest.SuuRestRequest
import eu.k5.soapui.streams.model.rest.SuuRestService
import eu.k5.soapui.visitor.listener.Environment
import eu.k5.soapui.visitor.listener.SuListener
import eu.k5.soapui.visitor.listener.SuTestSuiteListener
import eu.k5.soapui.visitor.listener.SuWsdlInterfaceListener

class DifferenceListener() : SuListener {
    override fun enterProject(env: Environment, project: SuProject) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun exitProject(suuProject: SuProject) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun createWsdlInterfaceListener(): SuWsdlInterfaceListener? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun createResourceListener(): SuuRestServiceListener {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun createTestSuiteListener(): SuTestSuiteListener? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}

class RestServiceDifferenceListener() : SuuRestServiceListener {
    override fun enter(restService: SuuRestService) {

    }

    override fun exit(restService: SuuRestService) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun enterResource(suuResource: SuuRestResource) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun exitResource(suuResource: SuuRestResource) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun enterMethod(suuRestMethod: SuuRestMethod) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun exitMethod(suuRestMethod: SuuRestMethod) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun handleRequest(suuRestRequest: SuuRestRequest) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}

class Difference {

    enum class Type {
        CHANGE, INSERT, DELETE
    }
}