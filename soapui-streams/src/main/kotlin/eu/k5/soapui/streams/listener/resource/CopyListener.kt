package eu.k5.soapui.streams.listener.resource

import eu.k5.soapui.streams.listener.copy.CopyRestServiceListener
import eu.k5.soapui.streams.listener.copy.CopyTestSuiteListener
import eu.k5.soapui.streams.model.SuListener
import eu.k5.soapui.streams.model.SuProject
import eu.k5.soapui.streams.model.test.SuuTestSuiteListener
import eu.k5.soapui.visitor.listener.Environment

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