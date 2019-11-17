package eu.k5.soapui.streams.model

import eu.k5.soapui.streams.model.rest.SuuRestServiceListener
import eu.k5.soapui.streams.model.test.SuuTestSuiteListener
import eu.k5.soapui.streams.Environment


interface SuListener {

    fun enterProject(env: Environment, project: SuProject)

    fun exitProject(suuProject: SuProject)
    

    fun createResourceListener(): SuuRestServiceListener

    fun createTestSuiteListener(): SuuTestSuiteListener

    companion object {

    }

}