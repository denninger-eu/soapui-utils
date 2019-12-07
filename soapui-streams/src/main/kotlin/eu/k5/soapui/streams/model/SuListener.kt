package eu.k5.soapui.streams.model

import eu.k5.soapui.streams.model.rest.SuuRestServiceListener
import eu.k5.soapui.streams.model.test.SuuTestSuiteListener
import eu.k5.soapui.streams.Environment
import eu.k5.soapui.streams.model.wsdl.SuuWsdlServiceListener


interface SuListener {

    fun enterProject(env: Environment, project: SuProject)

    fun exitProject(suuProject: SuProject)

    fun createRestServiceListener(): SuuRestServiceListener

    fun createTestSuiteListener(): SuuTestSuiteListener

    fun createWsdlServiceListener(): SuuWsdlServiceListener


    companion object {

    }

}