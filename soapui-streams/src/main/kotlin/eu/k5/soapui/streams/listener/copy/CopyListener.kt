package eu.k5.soapui.streams.listener.copy

import eu.k5.soapui.streams.model.SuListener
import eu.k5.soapui.streams.model.SuProject
import eu.k5.soapui.streams.model.rest.SuuRestServiceListener
import eu.k5.soapui.streams.model.test.SuuTestSuiteListener
import eu.k5.soapui.streams.Environment

class CopyListener(
    val suuProject: SuProject
) : SuListener {
    override fun enterProject(env: Environment, project: SuProject) {
    }

    override fun exitProject(suuProject: SuProject) {
    }


    override fun createResourceListener(): SuuRestServiceListener = CopyRestServiceListener(suuProject)

    override fun createTestSuiteListener(): SuuTestSuiteListener = CopyTestSuiteListener(suuProject)


}