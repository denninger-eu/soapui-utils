package eu.k5.soapui.streams.listener.sync

import eu.k5.soapui.streams.model.SuListener
import eu.k5.soapui.streams.model.SuProject
import eu.k5.soapui.streams.model.rest.SuuRestServiceListener
import eu.k5.soapui.streams.model.test.SuuTestSuiteListener
import eu.k5.soapui.streams.Environment
import eu.k5.soapui.streams.model.wsdl.SuuWsdlServiceListener

class CopyListener(
    val suuProject: SuProject
) : SuListener {

    override fun enterProject(env: Environment, project: SuProject) {
    }

    override fun exitProject(suuProject: SuProject) {
    }


    override fun createRestServiceListener(): SuuRestServiceListener =
        CopyRestServiceListener(suuProject)

    override fun createTestSuiteListener(): SuuTestSuiteListener =
        CopyTestSuiteListener(suuProject)

    override fun createWsdlServiceListener(): SuuWsdlServiceListener =
        CopyWsdlServiceListener(suuProject)


}