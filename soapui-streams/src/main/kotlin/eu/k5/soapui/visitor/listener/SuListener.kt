package eu.k5.soapui.visitor.listener

import eu.k5.soapui.streams.listener.resource.SuuRestServiceListener
import eu.k5.soapui.streams.model.SuProject
import eu.k5.soapui.streams.model.rest.SuuRestService


interface SuListener {

    fun enterProject(env: Environment, project: SuProject)

    fun exitProject(suuProject: SuProject)

    fun createWsdlInterfaceListener(): SuWsdlInterfaceListener?

    fun createResourceListener(): SuuRestServiceListener

    fun createTestSuiteListener(): SuTestSuiteListener?

    companion object {

    }

}