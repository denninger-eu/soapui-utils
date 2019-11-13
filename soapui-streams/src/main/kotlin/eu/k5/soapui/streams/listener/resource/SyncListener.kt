package eu.k5.soapui.streams.listener.resource

import eu.k5.soapui.streams.apply
import eu.k5.soapui.streams.listener.sync.RestServiceSyncListener
import eu.k5.soapui.streams.listener.sync.TestSuiteSyncListener
import eu.k5.soapui.streams.model.SuProject
import eu.k5.soapui.visitor.listener.Environment
import eu.k5.soapui.streams.model.SuListener
import eu.k5.soapui.streams.model.SuuProperties
import eu.k5.soapui.streams.model.rest.SuuRestParameters
import eu.k5.soapui.streams.model.test.SuuTestSuiteListener
import eu.k5.soapui.visitor.listener.SuWsdlInterfaceListener
import kotlin.collections.ArrayList

class SyncListener(
    private val referenceProject: SuProject
) : SuListener {
    private var environment: Environment? = null

    private var targetProject: SuProject? = null

    override fun enterProject(env: Environment, project: SuProject) {
        environment = env
        targetProject = project

        project.name = referenceProject.name
        project.description = referenceProject.description
        handleProperties(referenceProject.properties, project.properties)

    }

    override fun exitProject(target: SuProject) {
        handleMissingRestServices(target)

        handleMissingTestSuites(target)


    }

    private fun handleMissingTestSuites(target: SuProject) {
        val missingTestSuites = ArrayList(referenceProject.testSuites)
        target.testSuites.forEach { found -> missingTestSuites.removeIf { it.name == found.name } }

        val copyTestSuiteListener = CopyListener(target).createTestSuiteListener()
        for (missingTestSuite in missingTestSuites) {
            missingTestSuite.apply(copyTestSuiteListener)
        }
    }

    private fun handleMissingRestServices(target: SuProject) {
        val missingRestServices = ArrayList(referenceProject.restServices)
        target.restServices.forEach { found -> missingRestServices.removeIf { it.name == found.name } }

        val copyRestServiceListener = CopyListener(target).createResourceListener()
        for (missingRestService in missingRestServices) {
            missingRestService.apply(copyRestServiceListener)
        }
    }

    override fun createWsdlInterfaceListener(): SuWsdlInterfaceListener? {
        return SuWsdlInterfaceListener.NO_OP
    }

    override fun createResourceListener(): SuuRestServiceListener {
        return RestServiceSyncListener(
            environment!!,
            referenceProject,
            targetProject!!
        )
    }

    override fun createTestSuiteListener(): SuuTestSuiteListener {
        return TestSuiteSyncListener(
            environment!!,
            referenceProject,
            targetProject
        )
    }

    companion object {

        fun handleProperties(reference: SuuProperties, target: SuuProperties) {

            val missing = ArrayList<String>()
            for (targetProperty in target.properties) {
                if (!reference.hasProperty(targetProperty.name)) {
                    missing.add(targetProperty.name!!)
                }
            }
            missing.forEach { target.remove(it) }

            for (property in reference.properties) {
                target.addOrUpdate(property)
            }
        }
    }

}

