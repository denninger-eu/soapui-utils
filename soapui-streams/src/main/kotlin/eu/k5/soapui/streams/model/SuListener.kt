package eu.k5.soapui.streams.model

import eu.k5.soapui.streams.listener.resource.SuuRestServiceListener
import eu.k5.soapui.streams.model.test.SuuTestSuiteListener
import eu.k5.soapui.visitor.listener.Environment
import eu.k5.soapui.visitor.listener.SuWsdlInterfaceListener


interface SuListener {

    fun enterProject(env: Environment, project: SuProject)

    fun exitProject(suuProject: SuProject)

    fun createWsdlInterfaceListener(): SuWsdlInterfaceListener?

    fun createResourceListener(): SuuRestServiceListener

    fun createTestSuiteListener(): SuuTestSuiteListener

    companion object {

    }

}